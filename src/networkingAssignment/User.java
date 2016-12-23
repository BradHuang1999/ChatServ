package networkingAssignment;

import java.net.Socket;
import java.util.ArrayList;

// User class
class User {
	// Universal User Variables
	protected ArrayList<String> messages = new ArrayList<String>();
	protected ArrayList<String> messageSenders = new ArrayList<String>();
	protected ArrayList<String> messageTime = new ArrayList<String>();
	protected String username;
	protected String password;
	protected String nickname;
	protected String signature;
	protected String sendMessage;
	protected Socket socket;
	protected String messagingUser;
	protected String status;
	protected int messagingInt;
	
	// Constructors
	User(){
		this.status = "Offline";
	}
	User(Socket s){
		// Online status by default
		this.status = "Online";
		this.signature = "Status Message Here";
		// Setting socket
		this.socket = s;
	}

	/**
	 * @return the username
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	protected void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	protected void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the messageOfDay
	 */
	protected String getSignature() {
		return signature;
	}

	/**
	 * @param messageOfDay the messageOfDay to set
	 */
	protected void setSignature(String messageOfDay) {
		this.signature = messageOfDay;
	}

	/**
	 * @return the sendMessage
	 */
	protected String getSendMessage() {
		return sendMessage;
	}

	/**
	 * @param sendMessage the sendMessage to set
	 */
	protected void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	/**
	 * @return the status
	 */
	protected String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	protected void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the socket
	 */
	protected Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the messagingUser
	 */
	protected String getMessagingUser() {
		return messagingUser;
	}

	/**
	 * @param messagingUser the messagingUser to set
	 */
	protected void setMessagingUser(String messagingUser) {
		this.messagingUser = messagingUser;
	}

	/**
	 * @return the messagingInt
	 */
	protected int getMessagingInt() {
		return messagingInt;
	}

	/**
	 * @param messagingInt the messagingInt to set
	 */
	protected void setMessagingInt(int messagingInt) {
		this.messagingInt = messagingInt;
	}

	/**
	 * @return the nickname
	 */
	protected String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	protected void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
