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

public class ChatProgramServer {
	
	// Server Socket
	ServerSocket serverSock;
	// Boolean for accepting client connections
	static Boolean running = true;
	
	// ArrayLists of user objects and threads for clients
	public ArrayList<User> clients = new ArrayList<User>();
	public ArrayList<Thread> clientThreads = new ArrayList<Thread>();

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
				clientThreads.add(new Thread(new ConnectionHandler(clients.get(numClients))));
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
		private String inputMessage;
		/* ConnectionHandler
		 * Constructor
		 * @param User object with client information
		 */    
		ConnectionHandler(User user) { 
			this.user = user;
			// Assigns socket variable
			this.client = user.getSocket();
			try {  
				//assign all input and output to client
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
					inputMessage = input.readLine();
					// If the user is making a new account
					if(inputMessage == "newuser"){
						// Setting the password and username
						user.username = input.readLine();
						user.password = input.readLine();
					}else{
						// Searches for username
						for(int i = 0; i < clients.size(); i++){
							if(inputMessage == clients.get(i).getUsername()){
								user.username = inputMessage;
								// Checks Password
								inputMessage = input.readLine();
								if(inputMessage == clients.get(i).getPassword()){
									user.password = inputMessage;
								}else{
									output.println("Invalid Password");
									System.out.println("Invalid Password");
								}
							}else ;
						}
						if((user.username != inputMessage) || (user.password != inputMessage)){
							System.out.println("Invalid Username or Password");
							output.println("Invalid Username or Password");
						}
					}
				};
			}catch (IOException e){
				System.out.println("No Username or Password Received");
				e.printStackTrace();
			}
			// Loop to keep receiving messages from the client
			while(running) {  		
				// Receives the messages that were sent to client
				output.println(user.receivedMessage);
				output.flush(); 
				
				// Checks if the user is still connected
				try {
					if (input.ready()) {
						// Checks if the client is sending a message
						// First input is who they're messaging
						user.messagingUser = input.readLine();
						// Searches for the user in the arraylist
						for(int i = 0; i < clients.size(); i++){
							if(clients.get(i).getUsername() == user.messagingUser){
								user.messagingInt = i;
							}else ;
						}
						// Receives and sends the message
						user.sendMessage = input.readLine();
						user.sendMessage = (user.username + ": " + user.sendMessage);
						clients.get(user.messagingInt).receivedMessage = user.sendMessage;
						System.out.println("msg from " + user.username + "to " + clients.get(user.messagingInt).username + ": " + user.sendMessage);
						
					}
				}catch (IOException e) { 
					System.out.println("Failed to receive msg from the client");
					e.printStackTrace();
				}
			}
		} // end of run()
	} //end of inner class   
} //end of ChatProgramServer class