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
		String host = "127.0.0.1";
		int port = 10101;

		PrintWriter out = null;
		Scanner sock_in = null;
		Scanner in = null;
		
		try {
			clientSocket = new Socket(host, port);  //create new socket connected to host on port 10101
		} catch (IOException e) {
			System.err.println("Could not connect to " + host + " on port: "+port);
			System.exit(1);
		}
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);  //get output stream and wrap in PrintWriter for convenience
			sock_in = new Scanner( new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));  //get socket input stream (and wrap in BuffereReader for efficiency, wrap in Scanner for convenience)
			
			in = new Scanner(System.in);

			inputLine = in.nextLine();
			
			out.println(inputLine);
			
			returnLine = sock_in.nextLine();
			
			System.out.println( returnLine );
			
			in.close();
			clientSocket.close();
			
		}
		
		catch (IOException e) {
			
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
