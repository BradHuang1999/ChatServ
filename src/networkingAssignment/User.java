package networkingAssignment;

import java.net.Socket;

// User class
class User {
	// Universal User Variables
	protected String username;
	protected String password;
	protected String messageOfDay;
	protected String sendMessage;
	protected Socket socket;
	protected String messagingUser;
	protected String receivedMessage;
	protected int messagingInt;
	protected int status;
	
	// Constructor
	User(Socket s){
		// Online status by default
		this.status = 1;
		this.messageOfDay = "Status Message Here";
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
	protected String getMessageOfDay() {
		return messageOfDay;
	}

	/**
	 * @param messageOfDay the messageOfDay to set
	 */
	protected void setMessageOfDay(String messageOfDay) {
		this.messageOfDay = messageOfDay;
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
	protected int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	protected void setStatus(int status) {
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
	 * @return the receivedMessage
	 */
	protected String getReceivedMessage() {
		return receivedMessage;
	}

	/**
	 * @param receivedMessage the receivedMessage to set
	 */
	protected void setReceivedMessage(String receivedMessage) {
		this.receivedMessage = receivedMessage;
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
	
}
