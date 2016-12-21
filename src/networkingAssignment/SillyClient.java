package networkingAssignment;

/* [SillyClient.java]
 * Description: This is an example of a chat client.
 * The program receives and send a single message to a server 
 * @author Mangat
 * @version 1.0a
 */

//imports for network communication
import java.io.*;
import java.net.*;


class SillyClient {

	Socket mySocket; //socket for connection
	BufferedReader input; //reader for network stream
	PrintWriter output;  //printwriter for network output
	boolean running = true; //thread status via boolean

	/** Main
	 * @param args parameters from command line
	 */
	public static void main (String[] args) {
		SillyClient client = new SillyClient(); //start the client
		client.go(); //begin the connection}
	}
	
	/** Go
	 * Starts the client
	 */
	public void go() {
		//Create a socket (try-catch required)
		System.out.println("Attempting to make a connection..");

		try {
			mySocket = new Socket("127.0.0.1",5000); //attempt socket connection (local address). This will wait until a connection is made

			InputStreamReader stream1= new InputStreamReader(mySocket.getInputStream()); //Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream

		} catch (IOException e) {  //connection error occured
			System.out.println("Connection to Server Failed");
			e.printStackTrace();
		}

		System.out.println("Connection made.");

		// Send a message to the server (Once)
		output.println("Hi. I am a client!");

		output.flush();  //flush (very important)

		while(running) {  // loop unit a message is received
			try {
				if (input.ready()) { //check for an incoming messge
					String msg;          
					msg = input.readLine(); //read the message
					System.out.println("msg from server: " + msg);   
					running=false; //quit the program
				}
			} catch (IOException e) { 
				System.out.println("Failed to receive msg from the server");
				e.printStackTrace();
			}
		}
		
		try {  //after leaving the main loop we need to close all the sockets
			input.close();
			output.close();
			mySocket.close();
		}catch (Exception e) { 
			System.out.println("Failed to close socket");
		}
		
	}
}
