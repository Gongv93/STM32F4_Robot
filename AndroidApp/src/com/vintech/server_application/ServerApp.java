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
							// Creates Server socket
							serverSocket = new ServerSocket(10101);	
							changeText( statusTV, "Waiting for Connection..." );
							
							// Wait for handshake
							Socket connSocket = serverSocket.accept();
							changeText( statusTV, "ON" );
							
							
							out = new PrintWriter(connSocket.getOutputStream(), true); 
							in = new BufferedReader(
									new InputStreamReader(
											connSocket.getInputStream()));

							// Loop where we get input from the client
							// If we input "exit" then we close the server
							// If we don't have the right format we return and error
							// If we do have the right format we echo the command back
							while( (inputLine = in.readLine().toString()) != null ) {
								Log.i( "Input String", inputLine );
								if( inputLine.matches("exit") ) {
									break;
								}
								
								// Parse command line to get the command and the time
								try {
									cmdParser = new CommandParser(inputLine);
									changeText( currentCommandTV, "command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
									Log.i( "Output String", "command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
									out.println("command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
									Log.i( "Output String", "Outed" );
								} catch (CommandException e) {
									out.println( "Command did not match format." );
									changeText( currentCommandTV, "Command did not match format." );
									Log.i( "Error", "Command did not match format." );
								}
								
							}
							
							// When the server closes we change the text to let the user know
							changeText( currentCommandTV, "Waiting for command." );
							changeText( statusTV, "OFF" );
						
						} catch (IOException e) {
							changeText( statusTV, "Couldn't create server socket." );
						}
						
						// Closes connections
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
				
				// Starts the thread
				Thread myThread = new Thread(runnable);
				myThread.start();
		
			}
			
		});
		
	}
	
	private void changeText( final TextView TV, final String s ) {
		// Changes and textview on the app
		runOnUiThread( new Runnable() {
			@Override
			public void run() {
				TV.setText(s);
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
