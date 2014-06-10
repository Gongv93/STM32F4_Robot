import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {

	public static void main(String[] args) {
		Socket clientSocket = null;
		String inputLine;
		String returnLine;
		String host;
		int port;

		PrintWriter out = null;
		Scanner sock_in = null;
		Scanner in = null;
		
		// Localhost 127.0.0.1
		
		try {
			
			in = new Scanner(System.in);
			
			System.out.println( "Enter IP: " );
			host = in.nextLine();
			System.out.println( "Enter Port" );
			port = in.nextInt();
			
			System.out.println( "Attempting to connect...");
			clientSocket = new Socket(host, port);
			
			System.out.println( "Connected!" );
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);  //get output stream and wrap in PrintWriter for convenience
			sock_in = new Scanner( new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));  //get socket input stream (and wrap in BuffereReader for efficiency, wrap in Scanner for convenience)

			inputLine = in.nextLine();
			
			while(true) {

				inputLine = in.nextLine();
				
				out.println(inputLine);
				
				if( inputLine.matches("exit") ) {
					break;
				}
				
				returnLine = sock_in.nextLine();
				
				System.out.println( returnLine );
			
			}
			
			in.close();
			clientSocket.close();
			
		}
		
		catch (IOException e) {
			System.err.println("Could not connect");
			System.exit(1);
		}
		finally {
			if(in!=null)
			{
				in.close();
			}
			if(out!=null)
			{
				out.close();
			}
			if(sock_in!=null)
			{
				sock_in.close();
			}
		}
		
		
	}

}
