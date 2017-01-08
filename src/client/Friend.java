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
 * Created by Brad Huang on 1/6/2017.
 */
class Friend extends JPanel implements ListCellRenderer{
    private String nickName;
    private String userName;
    private String status;
    private String signature;

    private JLabel lblNickname;
    private JLabel lblMsn;

    private ChatGUI chat;
    private File chatHistory;

    Friend(){
    }

    Friend(String name, String signature, String username, String status, File ch, ChatServ client) throws IOException{
        this.nickName = name;
        this.userName = username;
        this.status = status;
        this.signature = signature;
        this.chatHistory = ch;

        this.setLayout(null);
        this.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK)));

        lblNickname = new JLabel(statusFormat(this.nickName, this.status));
        lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNickname.setBounds(15, 15, 250, 25);

        lblMsn = new JLabel(this.signature);
        lblMsn.setFont(new Font("Courier New", Font.PLAIN, 15));
        lblMsn.setBounds(15, 42, 250, 20);

        this.add(lblNickname);
        this.add(lblMsn);

        this.chat = new ChatGUI(this, client);
    }

    public void update(){
        lblNickname.setText(statusFormat(this.nickName, this.status));
        lblMsn.setText(this.signature);
    }

    public String statusFormat(String nickName, String status){
        switch (status){
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

    public void receiveMessage(String title, String message) throws IOException{
        PrintWriter chPw = new PrintWriter(new FileWriter(this.chatHistory, true));
        chPw.println(title);
        chPw.println(message);
        chPw.close();

        this.chat.appendToPane(title, Color.DARK_GRAY);
        this.chat.appendToPane(message, Color.BLACK);
        this.chat.appendToPane("", Color.BLACK);

        this.chat.setVisible(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        JPanel renderer = (JPanel)value;
        renderer.setBackground(isSelected ? Color.LIGHT_GRAY : list.getBackground());
        return renderer;
    }

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getUserName(){
        return userName;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setSignature(String signature){
        this.signature = signature;
    }

    public ChatGUI getChat(){
        return chat;
    }

    public File getChatHistory(){
        return chatHistory;
    }

}