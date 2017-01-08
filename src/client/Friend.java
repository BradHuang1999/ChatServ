package client;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Friend Class for data storage and cell rendering
 * @author Brad Huang
 */
class Friend extends JPanel implements ListCellRenderer {
    // user variables
    private String nickName;
    private String userName;
    private String status;
    private String signature;

    // GUI variables
    private JLabel lblNickname;
    private JLabel lblMsn;

    // chat window
    private ChatGUI chat;

    // chat history files
    private File chatHistory;

    /**
     * empty constructor for cellRenderer
     */
    Friend() {
    }

    /**
     * constructor
     *
     * @param name      client name
     * @param signature client signature
     * @param username  client username
     * @param status    client status
     * @param ch        client chatHistory
     * @param client    client handler to control I/O
     * @throws IOException
     */
    Friend(String name, String signature, String username, String status, File ch, ChatServ client) throws IOException {
        this.nickName = name;
        this.userName = username;
        this.status = status;
        this.signature = signature;
        this.chatHistory = ch;

        this.setLayout(null);       // GUI stuff
        this.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK)));

        lblNickname = new JLabel(statusFormat(this.nickName, this.status));
        lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNickname.setBounds(15, 15, 250, 25);

        lblMsn = new JLabel(this.signature);
        lblMsn.setFont(new Font("Courier New", Font.PLAIN, 15));
        lblMsn.setBounds(15, 42, 250, 20);

        this.add(lblNickname);
        this.add(lblMsn);

        this.chat = new ChatGUI(this, client);      // set up chat window
    }

    /**
     * update the client nickname and signature labels
     */
    public void update() {
        lblNickname.setText(statusFormat(this.nickName, this.status));
        lblMsn.setText(this.signature);
    }

    /**
     * format the client status using html
     *
     * @param nickName client nickname
     * @param status   client status
     * @return the formatted version of the nickname and the status
     */
    public String statusFormat(String nickName, String status) {
        switch (status) {
            case "Online":
                return "<html>" + nickName + " <font color=green>(Online)</font></html>";
            case "Busy":
                return "<html>" + nickName + " <font color=orange>(Busy)</font></html>";
            case "Offline":
                return "<html>" + nickName + " <font color=red>(Offline)</font></html>";
            default:
                return status;
        }
    }

    /**
     * receive the messages from server
     *
     * @param title   the nickname and the time stamp of the message
     * @param message the message
     * @throws IOException
     */
    public void receiveMessage(String title, String message) throws IOException {
        PrintWriter chPw = new PrintWriter(new FileWriter(this.chatHistory, true));
        chPw.println(title);
        chPw.println(message);
        chPw.close();

        this.chat.appendToPane(title, Color.DARK_GRAY);     // append the message to the chat window
        this.chat.appendToPane(message, Color.BLACK);
        this.chat.appendToPane("", Color.BLACK);

        this.chat.setVisible(true);     // display the message
    }

    @Override
    /**
     * ser up cellRenderer
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel renderer = (JPanel) value;
        renderer.setBackground(isSelected ? Color.LIGHT_GRAY : list.getBackground());
        return renderer;
    }

    /**
     * get client nickname
     *
     * @return nickname of the client
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * set up the nickname
     *
     * @param nickName nickname of the client
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * get username of the client
     *
     * @return username of the client
     */
    public String getUserName() {
        return userName;
    }

    /**
     * get status of the client
     *
     * @return client status
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status of the client
     *
     * @param status the status of the client
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * set the signature of the client
     *
     * @param signature client signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * get chat UI
     *
     * @return chat UI of the friend
     */
    public ChatGUI getChat() {
        return chat;
    }

    /**
     * get the file of the chat history between the client and the friend
     *
     * @return
     */
    public File getChatHistory() {
        return chatHistory;
    }

}