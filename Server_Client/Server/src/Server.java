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
		String inputLine;
		String command;
		int i;

		
		try {
			serverSocket = new ServerSocket(10101);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't create server socket.");
		}
		
		while(true) {
			try {
				connSocket = serverSocket.accept();
			} catch(IOException e) {
				System.out.println("Accept failed.");
				System.exit(1);
			}
			
			try {
				out = new PrintWriter(connSocket.getOutputStream(), true);  //get the socket's output stream and wrap in a PrintWriter for convenience
	
				in = new BufferedReader(
						new InputStreamReader(
								connSocket.getInputStream()));  //Get the socket's input stream and wrap in BufferedReader
				
				inputLine = in.readLine();
				
				
				
				
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					if(in!=null)
					{
						in.close();
					}
					if(out!=null)
					{
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
}
