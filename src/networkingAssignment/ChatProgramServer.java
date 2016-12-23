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

public class ChatProgramServer{

	// Server Socket
	ServerSocket serverSock;
	// Boolean for accepting client connections
	static Boolean running = true;

	// ArrayLists of user objects and threads for clients
	public ArrayList<User> clients = new ArrayList<User>();
	public ArrayList<Thread> clientThreads = new ArrayList<Thread>();

	/**
	 * Main
	 *
	 * @param args parameters from command line
	 */
	public static void main(String[] args){
		new ChatProgramServer().go(); //start the server
	}

	/**
	 * Go
	 * Starts the server
	 */
	public void go(){

		System.out.println("Waiting for a client connection..");

		// Variable for total number of clients
		int numClients = -1;

		try {
			// Assigns 5000 port to server
			serverSock = new ServerSocket(5000);
			// Continually loops to accept all clients
			while (running){
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
		} catch (Exception e){
			System.out.println("Error accepting connection");
			System.exit(-1);
		}
	}

	//***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable{
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
		ConnectionHandler(User user){
			this.user = user;
			// Assigns socket variable
			this.client = user.getSocket();
			try {
				//assign all input and output to client
				this.output = new PrintWriter(client.getOutputStream());
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(stream);
			} catch (IOException e){
				e.printStackTrace();
			}
			running = true;
		} //end of constructor


		/* run
		 * executed on start of thread
		 */
		public void run(){
			while (running){
				// Checks username and password
				try {
					if (input.ready()){
						inputMessage = input.readLine();
						// If the user is making a new account
						if (inputMessage.equals("CreateUser")){
							// Setting the username, nickname, and password
							user.username = input.readLine();
							// Checks if the username is in use
							for (int i = 0; i < clients.size(); i++){
								if (clients.get(i).username.equals(user.username)){
									output.println("Username already in use");
									output.flush();
								} else {
									// Sets Nickname
									user.nickname = input.readLine();
									// Sets and checks passwords
									user.password = input.readLine();
									inputMessage = input.readLine();
									if (user.password.equals(inputMessage)){
										// Confirms user creation
										output.println("User created");
										output.flush();
									} else {
										// If passwords don't match
										output.println("Passwords do not match");
										output.flush();
									}
								}
							}
						} else if (inputMessage.equals("Login")){
							// Takes in the username
							inputMessage = input.readLine();
							// Searches for username
							for (int i = 0; i < clients.size(); i++){
								// If username matches a known user
								if (inputMessage.equals(clients.get(i).getUsername())){
									// Checks for password
									inputMessage = input.readLine();
									if (inputMessage.equals(clients.get(i).getPassword())){
										// Accepts user if passwords match
										user = clients.get(i);
										clients.remove(i);
										clients.add(user);
										output.println("Successful");
										output.flush();
										running = false;
									} else {
										output.println("Unsuccessful");
										output.flush();
									}
								} else ;
							}
							// If username is not found in the database
							if (user == null){
								output.println("Unsuccessful");
								output.flush();
							}
							// If client doesn't send valid username command
						} else {
							output.println("Invalid Command Received");
							output.flush();
						}
					}
					// If nothing is sent to client
				} catch (IOException e){
					System.out.println("No Username or Password Received");
					e.printStackTrace();
				}
			}
			running = true;
			// Loop to keep receiving messages from the client
			while (running){
				// Sends all the friends list info to the client
				output.println("Friends List Info");
				output.flush();
				for (int i = 0; i < clients.size(); i++){
					output.println(clients.get(i).nickname);
					output.flush();
					output.println(clients.get(i).signature);
					output.flush();
					output.println(clients.get(i).status);
					output.flush();
				}
				// Signals end of friends list info
				output.println("End of Friends List");
				output.flush();
				// Sends all the messages in the client queue to the client
				for (int i = 0; i < user.messages.size(); i++){
					// Nickname of person messaging
					output.println(user.messageSenders.get(i));
					output.flush();
					// Timestamp of message
					output.println(user.messageTime.get(i));
					output.flush();
					// Actual message
					output.println(user.messages.get(i));
					output.flush();
				}
				// Signals end of new messages
				output.println("End of Messages");
				output.flush();
				// Removes messages
				user.messages.clear();

				// Checks if the user is still connected
				try {
					if (input.ready()){
						// Checks if the client is sending a message
						// First input is a command for what they want to do
						user.messagingUser = input.readLine();
						// If statement for what client wants to do
						// Sending a Message
						if (user.messagingUser.equals("SendMessage")){
							// Searches for the user in the arraylist
							user.messagingUser = input.readLine();
							for (int i = 0; i < clients.size(); i++){
								if (clients.get(i).getUsername().equals(user.messagingUser)){
									user.messagingInt = i;
								}
							}
							// Receives and sends the message
							user.sendMessage = input.readLine();
							clients.get(user.messagingInt).messageSenders.add(user.nickname);
							clients.get(user.messagingInt).messageTime.add(user.sendMessage);
							user.sendMessage = input.readLine();
							clients.get(user.messagingInt).messages.add(user.sendMessage);
							System.out.println("msg from " + user.username + "to " + clients.get(user.messagingInt).username + ": " + user.sendMessage);
						} else if (user.messagingUser.equals("ChangeSignature")){
							inputMessage = input.readLine();
							user.signature = inputMessage;
							output.println("Signature Changed");
							output.flush();
							// Setting Status To Busy
						} else if (user.messagingUser.equals("Busy")){
							user.status = "Busy";
							output.println("Status Changed");
							output.flush();
							// Setting Status to Online
						} else if (user.messagingUser.equals("Online")){
							user.status = "Online";
							output.println("Status Changed");
							output.flush();
						} else if (user.messagingUser.equals("Offline")){
							user.status = "Offline";
							output.println("Status Changed");
							output.flush();
						} else if (user.messagingUser.equals("Close")){
							user.status = "Offline";
							System.out.println("User " + user.username + " has disconnected");
							running = false;
						} else {
							output.println("Invalid Command Received");
							output.flush();
						}
					}
				} catch (IOException e){
					System.out.println("Failed to receive msg from the client");
					e.printStackTrace();
				}
			}
		} // end of run()
	} //end of ConnectionHandler inner class
}