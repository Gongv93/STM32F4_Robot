package com.vintech.server_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ServerApp extends Activity {

	private static final String PORT = "PORT";
	private int port;
	
	TextView statusTV;
	TextView currentCommandTV;
	EditText portET;
	Button startB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_app);
		
		if( savedInstanceState == null ) {
			port = 10101;
		}
		else {
			port = savedInstanceState.getInt(PORT);
		}
		
		portET = (EditText) findViewById(R.id.portEditText);
		startB = (Button) findViewById(R.id.startButton);
		statusTV = (TextView) findViewById(R.id.currentTextView);
		currentCommandTV = (TextView) findViewById(R.id.currentCommandTextView);
		
		addClickListenerButton();
		
		
	}
	
	private void addClickListenerButton() {
		startB.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Runnable runnable = new Runnable() {
					public void run() {
						ServerSocket serverSocket = null;
						PrintWriter out = null;
						BufferedReader in = null;
						CommandParser cmdParser = null;
						String inputLine;
						
						// Get port
						//port = Integer.parseInt(portET.toString());
						try {
							serverSocket = new ServerSocket(10101);
							//statusTV.setText("Waiting on connection.");
							Socket connSocket = serverSocket.accept();
							//statusTV.setText("ON");
							
							out = new PrintWriter(connSocket.getOutputStream(), true); 
							in = new BufferedReader(
									new InputStreamReader(
											connSocket.getInputStream()));

							while( (inputLine = in.readLine().toString()) != null ) {
								Log.i( "Input String", inputLine );
								if( inputLine.matches("exit") ) {
									break;
								}
								
								// Parse command line to get the command and the time
								try {
									cmdParser = new CommandParser(inputLine);
								} catch (CommandException e) {
									Log.i( "Error", "Command did not match format." );
								}
								
								//currentCommandTV.setText( "command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
								Log.i( "Output String", "command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
								out.println("command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
								Log.i( "Output String", "Outed" );
								
							}
							
							//statusTV.setText("OFF");
						
						} catch (IOException e) {
							//currentCommandTV.setText( "Couldn't create server socket." );
						}
						
						try {
							if(in!=null) {
								in.close();
							}
							if(out!=null) {
								out.close();
							}
						} catch (IOException e) {
		
						}
					}
				};
				Thread myThread = new Thread(runnable);
				myThread.start();
		
			}
			
		});
		
	}
	
	@Override
	protected void onSaveInstanceState( Bundle outState ) {
		super.onSaveInstanceState(outState);
		outState.putInt(PORT, port);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_app, menu);
		return true;
	}
	
	

}
