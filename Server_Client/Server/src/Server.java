import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket connSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		Scanner getInput = null;
		CommandParser cmdParser = null;
		String inputLine;
		int port;
		
		getInput = new Scanner( System.in );
		
		System.out.println( "Enter port:" );
		port = getInput.nextInt();
		
		try {
			
			System.out.println( "Attempting to create server socket...");
			serverSocket = new ServerSocket(port);
			System.out.println( "SUCCESS!\nWaiting to connect..." );
			connSocket = serverSocket.accept();
			System.out.println( "SUCCESS!" );
			
			out = new PrintWriter(connSocket.getOutputStream(), true); 

			in = new BufferedReader(
					new InputStreamReader(
							connSocket.getInputStream()));
			
			while( (inputLine = in.readLine().toString()) != null ) {
				
				System.out.println( "Server In: " + inputLine );
				
				if( inputLine.matches("exit") ) {
					break;
				}
				
				// Parse command line to get the command and the time
				try {
					cmdParser = new CommandParser(inputLine);
				} catch (CommandException e) {
					out.println( "Command did not match format." );
				}
				
				System.out.println("command: " + cmdParser.getCmd() + "\nTime: " + cmdParser.getTime() + "\n" );
				out.println("command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() + "\n" );
		
			}
			
			System.out.println( "Goodbye" );
		
		} catch (IOException e) {
			System.out.println("Couldn't create server socket.");
		}
		
		try {
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	


	}
}