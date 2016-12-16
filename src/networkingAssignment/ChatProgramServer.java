package networkingAssignment;

/* [ChatProgramServer.java]
 * Description: This is an example of a chat server.
 * The program  waits for a client and accepts a message. 
 * It then responds to the message and quits.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Mangat
 * @version 1.0a
 */

//imports for network communication
import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ChatProgramServer {

	ServerSocket serverSock;// server socket for connection
	static Boolean running = true;  // controls if the server is accepting clients

	/** Main
	 * @param args parameters from command line
	 */
	public static void main(String[] args) { 
		new ChatProgramServer().go(); //start the server
	}

	/** Go
	 * Starts the server
	 */
	public void go() { 

		System.out.println("Waiting for a client connection..");

		// Variable for total number of clients
		int numClients = -1;

		// ArrayLists of user objects and threads for clients
		ArrayList<User> clients = new ArrayList<User>();
		ArrayList<Thread> clientThreads = new ArrayList<Thread>();

		try {
			// Assigns 5000 port to server
			serverSock = new ServerSocket(5000);
			// serverSock.setSoTimeout(5000);  //5 second timeout
			// Continually loops to accept all clients
			while(running) {
				// Checks for outstanding messages to send to client
				//Insert code here to check user arraylist
				// Adds user to arraylist
				clients.add(new User(serverSock.accept()));  //wait for connection
				numClients++;
				System.out.println("New client connected");
				// Creates a separate thread for each user
				clientThreads.add(new Thread(new ConnectionHandler(clients.get(numClients)))); //create a thread for the new client and pass in the socket
				clientThreads.get(numClients).start(); //start the new thread
			}
		}catch(Exception e) { 
			System.out.println("Error accepting connection");
			System.exit(-1);
		}
	}

	//***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable { 
		private PrintWriter output; //assign printwriter to network stream
		private BufferedReader input; //Stream for network input
		private Socket client;  //keeps track of the client socket
		private boolean running;
		private User user;
		/* ConnectionHandler
		 * Constructor
		 * @param User object with client information
		 */    
		ConnectionHandler(User user) { 
			this.user = user;
			// Assigns socket variable
			this.client = user.getSocket(); 
			try {  //assign all connections to client
				this.output = new PrintWriter(client.getOutputStream());
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(stream);
			}catch(IOException e) {
				e.printStackTrace();        
			}            
			running=true;
		} //end of constructor


		/* run
		 * executed on start of thread
		 */
		public void run() {  
			// Checks username and password
			try {
				if(input.ready()){
					
				};
			}catch (IOException e){
				System.out.println("No Username or Password Received");
				e.printStackTrace();
			}
			//Get a message from the client
			while(running) {  // loop unit a message is received        
				try {
					if (input.ready()) { //check for an incoming message
						user.sendMessage = input.readLine();  //get a message from the client
						System.out.println("msg from client: " + user.sendMessage); 
						running=false; //stop receiving messages
					}
				}catch (IOException e) { 
					System.out.println("Failed to receive msg from the client");
					e.printStackTrace();
				}
			}    

			//Send a message to the client
			output.println("We got your message! Goodbye.");
			output.flush(); 

			//close the socket
			try {
				input.close();
				output.close();
				client.close();
			}catch (Exception e) { 
				System.out.println("Failed to close socket");
			}
		} // end of run()
	} //end of inner class   
} //end of ChatProgramServer class