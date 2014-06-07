import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket connSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		CommandParser cmdParser = null;
		String inputLine;

		try {
			serverSocket = new ServerSocket(10101);
			connSocket = serverSocket.accept();
			out = new PrintWriter(connSocket.getOutputStream(), true); 

			in = new BufferedReader(
					new InputStreamReader(
							connSocket.getInputStream()));
			
			inputLine = in.readLine();
				
			System.out.println( "Server In: " + inputLine );
			
			// Parse command line to get the command and the time
			try {
				cmdParser = new CommandParser(inputLine);
			} catch (CommandException e) {
				out.println( "Command did not match format." );
				e.printStackTrace();
			}
			
			
			System.out.println("command: " + cmdParser.getCmd() + "\nTime: " + cmdParser.getTime() );
			out.println("command: " + cmdParser.getCmd() + ", Time: " + cmdParser.getTime() );
		
			
			System.out.println( "Goodbye" );
		
		} catch (IOException e) {
			e.printStackTrace();
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