package networkingAssignment;

import java.awt.*;
import javax.swing.*;


class ChatProgramClient {

	JButton sendButton, clearButton;
	JTextField typeField;
	JTextArea msgArea;  
	JPanel southPanel;

	public static void main(String[] args) { 
		new ChatProgramClient().go();
	}

	public void go() {
		JFrame window = new JFrame("Chat Client");
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,0));

		sendButton = new JButton("SEND");
		clearButton = new JButton("<html><i>CLEAR</i></html>");

		JLabel errorLabel = new JLabel("");

		typeField = new JTextField(10);

		msgArea = new JTextArea();
		msgArea.setText("<html><i>lol</i></html>");

		southPanel.add(typeField);
		southPanel.add(sendButton);
		southPanel.add(errorLabel);
		southPanel.add(clearButton);

		window.add(BorderLayout.CENTER,msgArea);
		window.add(BorderLayout.SOUTH,southPanel);

		window.setSize(400,500);
		window.setVisible(true);

		// call a method that connects to the server 
		// after connecting loop and keep appending[.append()] to the JTextArea
	}

	//****** Inner Classes for Action Listeners ****

	// To complete this you will need to add action listeners to both buttons
	// clear - clears the textfield
	// send - send msg to server (also flush), then clear the JTextField

}