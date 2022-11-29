import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class PrimeServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Creates ServerSocket and a shutdown boolean
		ServerSocket server = null;
		boolean shutdown = false;
		
		//Connecting to the server
		try {
			server = new ServerSocket(1237);
			System.out.println("Connection made");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		//While server is connected
		while (!shutdown)
		{
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			//Gets a connection
			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				int n = input.read();
				byte[] data = new byte [n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
			
				String response = PrimeNumber.Detect(clientInput);
				output.write(response.getBytes());
				
				client.close();
				
				//If the client enters shutdown, shutdowns the server
				if(clientInput.equalsIgnoreCase("shutdown"))
				{
					shutdown = true;
					System.out.println("Server is shutting down");
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}

}

//Class to see if the number is prime, I chose to only handle cases where the number is positive. If it is negative, the user must re enter a positive number.
class PrimeNumber {
	
	public static String Detect(String userString) {
		String answer = "";
		boolean prime = true;
		
		try {
			int n = Integer.parseInt(userString);
			
			if (n < 0)
			{
				answer = "Please enter a positive number";
				prime = false;
			}
			
			if (n==0 || n == 1)
			{
				answer = "This number is not a prime number";
			}
			else 
			{
				for (int i = 2; i < (n/2); i++)
				{
					if (n % i == 0)
					{
						answer = "This number is not a prime number";
						prime = false;
						break;
					}
				}
			if (prime)
			{
				answer = "This number is a prime number";
			}
				
			}
			
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			answer = "Must enter a number";

		}
		
		return answer;
	}
}
