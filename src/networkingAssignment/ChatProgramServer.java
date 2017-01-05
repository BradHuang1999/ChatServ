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
import java.util.Scanner;

public class ChatProgramServer{

	// Server Socket
	private ServerSocket serverSock;

	// Boolean for accepting client connections
	private boolean running = true;

	// ArrayLists of user objects and threads for clients
	private ArrayList<User> clients = new ArrayList<User>();
	private ArrayList<Thread> clientThreads = new ArrayList<Thread>();

	// File and PrintWriter
	private File users = new File("resources/users.txt");
	private PrintWriter usersFile = new PrintWriter(new FileWriter(users, true));

	// number of clients
	private int numClients = 0;
	private int userNum = 0;

	// socket port number
	public static final int socketNum = 1234;

	public ChatProgramServer() throws IOException{

		// Variable for total number of clients
		try {
			// Assigns 5000 port to server
			serverSock = new ServerSocket(socketNum);
			// Read users from text file
			Scanner usersReader = new Scanner(users);
			while (usersReader.hasNextLine()){
				clients.add(new User());
				System.out.println(clients.size());
				clients.get(userNum).setUsername(usersReader.nextLine());
				clients.get(userNum).setNickname(usersReader.nextLine());
				clients.get(userNum).setPassword(usersReader.nextLine());
				clients.get(userNum).setSignature(usersReader.nextLine());
				userNum++;
			}
			usersReader.close();

			//			// testing
			//			for (int i = 0; i < clients.size(); i++){
			//				System.out.println(clients.get(i).getUsername() + " " + clients.get(i).getNickname() + " " + clients.get(i).getPassword());
			//			}

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.out.println("Closing");
				usersFile.close();
			}, "Shutdown-thread"));

			System.out.println("Waiting for client connection..");

		} catch (IOException e){
			System.out.println("Initiation Failed");
			System.exit(-1);
		}
	}

	/**
	 * Go
	 * Starts the server
	 */
	public void go() throws IOException{
		// Continually loops to accept all clients
		Socket tempSocket;
		try{
			while (running){
				// Adds user to arraylist
				tempSocket = serverSock.accept();
				System.out.println(tempSocket);
				clients.add(new User());  //wait for connection
				clients.get(userNum).setSocket(tempSocket);
				// Creates a separate thread for each user
				clientThreads.add(new Thread(new ConnectionHandler(clients.get(userNum))));
				clientThreads.get(numClients).start(); //start the new thread
				userNum++;
				numClients++;

				System.out.println("New client connected");

			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Error accepting connection");
			System.exit(-1);
		}
	}

	//***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable{
		private PrintWriter output; //assign printwriter to network stream
		private ServerBufferedReader input; //Stream for network input
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
				System.out.println(this.client);
				System.out.println(this.output);
				System.out.println(this.client.getOutputStream());

				this.output = new PrintWriter(client.getOutputStream());
				InputStreamReader stream = new InputStreamReader(this.client.getInputStream());
				this.input = new ServerBufferedReader(stream);
			} catch (IOException e){
				e.printStackTrace();
			}
			running = true;
		}


		/* run
		 * executed on start of thread
		 */
		public void run(){
            boolean waiting = true;

			while (waiting){
				// Checks username and password
				try {
					if (input.ready()){
						inputMessage = input.readLine();

						// If the user is making a new account
						if (inputMessage.equals("CreateUser")){
							// Setting the username, nickname, and password
							inputMessage = input.readLine();

							boolean usernameInUse = false;

							// Checks if the username is in use
							for (int i = 0; i < clients.size() - 1; i++){
								if (clients.get(i).getUsername().equals(inputMessage)){
									inputMessage = input.readLine();
									inputMessage = input.readLine();
									inputMessage = input.readLine();
									output.println("Username already in use");
									output.flush();
									usernameInUse = true;
									break;
								}
							}

							if (!usernameInUse){
								// Sets Username
								user.setUsername(inputMessage);
								// Sets Nickname
								inputMessage = input.readLine();
								user.setNickname(inputMessage);
								// Sets and checks passwords
								inputMessage = input.readLine();
								user.setPassword(inputMessage);
								// Confirmation Password
								inputMessage = input.readLine();

								if (user.getPassword().equals(inputMessage)){
									// Updates textfile with users
									usersFile.println(user.getUsername());
									usersFile.println(user.getNickname());
									usersFile.println(user.getPassword());
                                    usersFile.println(user.getSignature());
									// Confirms user creation
									output.println("User created");
									output.flush();
								} else {
									// If passwords don't match
									output.println("Passwords do not match");
									output.flush();
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
										output.println(user.getUsername() + "\n" + user.getNickname() + "\n" + user.getSignature());
										output.flush();

										waiting = false;
										break;
									} else {
										output.println("Unsuccessful");
										output.flush();
									}
								}
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
				} catch (NullPointerException e){
					System.out.println("Null Pointer Exception");
					output.flush();
					e.printStackTrace();
				}
			}

			waiting = true;

			// Loop to keep receiving messages from the client
			while (waiting){
				// Sends all the friends list info to the client
				output.println("Friends List Info");
				output.flush();
				for (int i = 0; i < clients.size(); i++){
					output.println(clients.get(i).getUsername());
					output.println(clients.get(i).getNickname());
					output.println(clients.get(i).getSignature());
					output.println(clients.get(i).getStatus());
				}
				// Signals end of friends list info
				output.println("End of Friends List");
				output.flush();

				// Sends all the messages in the client queue to the client
				for (int i = 0; i < user.messages.size(); i++){
					// Nickname of person messaging
					output.println(user.messageSenders.get(i));
					// Timestamp of message
					output.println(user.messageTime.get(i));
					// Actual message
					output.println(user.messages.get(i));
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
						user.setMessagingUser(input.readLine());

						// If statement for what client wants to do
						// Sending a Message
						if (user.getMessagingUser().equals("SendMessage")){
							// Searches for the user in the arraylist
							user.setMessagingUser(input.readLine());
							for (int i = 0; i < clients.size(); i++){
								if (clients.get(i).getUsername().equals(user.getMessagingUser())){
									user.setMessagingInt(i);
								}
							}

							// Receives and sends the message
							user.sendMessage = input.readLine();
							clients.get(user.getMessagingInt()).messageSenders.add(user.getNickname());
							clients.get(user.getMessagingInt()).messageTime.add(user.sendMessage);
							user.sendMessage = input.readLine();
							clients.get(user.getMessagingInt()).messages.add(user.sendMessage);
							System.out.println("msg from " + user.getUsername() + "to " + clients.get(user.getMessagingInt()).getUsername() + ": " + user.sendMessage);

						} else if (user.getMessagingUser().equals("ChangeSignature")){
							inputMessage = input.readLine();
							user.setSignature(inputMessage);
							output.println("Signature Changed");
							output.flush();

							// Setting Status To Busy
						} else if (user.getMessagingUser().equals("Busy")){
							user.setStatus("Busy");
							output.println("Status Changed");
							output.flush();

							// Setting Status to Online
						} else if (user.getMessagingUser().equals("Online")){
							user.setStatus("Online");
							output.println("Status Changed");
							output.flush();

						} else if (user.getMessagingUser().equals("Offline")){
							user.setStatus("Offline");
							output.println("Status Changed");
							output.flush();

						} else if (user.getMessagingUser().equals("Close")){
							user.setStatus("Offline");
							System.out.println("User " + user.getUsername() + " has disconnected");
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

				try{
				    Thread.sleep(10);
                } catch (InterruptedException e){
                }
			}
		}
	}

	class ServerBufferedReader extends BufferedReader{
		public ServerBufferedReader(Reader in){
			super(in);
		}

		@Override
		public String readLine() throws IOException{
			String line = super.readLine();
			System.out.println("Client: " + line);
			return line;
		}
	}

	/**
	 * Main
	 * main program
	 * @param args parameters from command line
	 */
	public static void main(String[] args) throws IOException{
		new ChatProgramServer().go(); //start the server
	}
}