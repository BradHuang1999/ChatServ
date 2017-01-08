package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brad Huang on 12/21/2016.
 */
public class FriendListGUI extends JFrame{

    private ChatServ client;
    private String signature;
    private String nickname;
    private String username;
    private JTextField txtSignatureHelloworld;

    private JScrollPane scrollPane;
    private JList<Friend> list;
    private DefaultListModel<Friend> lm;

    public FriendListGUI(ChatServ client, String username, String nickname, String sig){

        this.client = client;
        this.username = username;
        this.nickname = nickname;
        this.signature = sig;

        (new File("userfiles/" + this.username)).mkdir();

        getContentPane().setLayout(null);
        setTitle("ChatServ Friend List");

        setSize(350, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowListener(){
            public void windowClosed(WindowEvent e){}
            public void windowOpened(WindowEvent e){}
            public void windowClosing(WindowEvent e){
                client.close();
            }
            public void windowIconified(WindowEvent e){}
            public void windowDeiconified(WindowEvent e){}
            public void windowActivated(WindowEvent e){}
            public void windowDeactivated(WindowEvent e){}
        });

        JLabel lblNickname = new JLabel(this.nickname);
        lblNickname.setFont(new Font("Impact", Font.PLAIN, 20));
        lblNickname.setBounds(10, 11, 171, 35);
        getContentPane().add(lblNickname);

        JLabel lblNewLabel = new JLabel("@ " + this.username);
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        lblNewLabel.setBounds(30, 51, 89, 14);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Online");
        lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));
        lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(240, 49, 77, 14);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Signature:");
        lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(30, 81, 80, 14);
        getContentPane().add(lblNewLabel_2);

        txtSignatureHelloworld = new JTextField();
        txtSignatureHelloworld.setText(this.signature);
        txtSignatureHelloworld.setBounds(90, 78, 223, 24);
        getContentPane().add(txtSignatureHelloworld);
        txtSignatureHelloworld.setColumns(10);

        txtSignatureHelloworld.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            public void keyPressed(KeyEvent e){}
            public void keyReleased(KeyEvent e){
                if (!txtSignatureHelloworld.getText().equals(signature)){
                    signature = txtSignatureHelloworld.getText();
                    client.output.println("ChangeSignature" + "\n" + signature);
                    client.output.flush();
                }
            }
        });

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 115, 350, 2);
        getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 150, 350, 2);
        getContentPane().add(separator_1);

        JButton btnNewButton = new JButton("");
        ImageIcon im1 = new ImageIcon("resources/online.png");
        btnNewButton.setIcon(im1);
        btnNewButton.setBounds(10, 122, 33, 23);
        getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(e -> {
            client.output.println("Online");
            client.output.flush();

            lblNewLabel_1.setText("Online");
            lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));

            client.running = true;
        });

        JButton button = new JButton("");
        ImageIcon im2 = new ImageIcon("resources/busy.png");
        button.setIcon(im2);
        button.setBounds(53, 122, 33, 23);
        getContentPane().add(button);
        button.addActionListener(e -> {
            client.output.println("Busy");
            client.output.flush();

            lblNewLabel_1.setText("Busy");
            lblNewLabel_1.setIcon(new ImageIcon("resources/red.png"));

            client.running = true;
        });

        JButton button_1 = new JButton("");
        ImageIcon im3 = new ImageIcon("resources/offline.png");
        button_1.setIcon(im3);
        button_1.setBounds(96, 122, 33, 23);
        getContentPane().add(button_1);
        button_1.addActionListener(e -> {
            client.output.println("Offline");
            client.output.flush();

            lblNewLabel_1.setText("Offline");
            lblNewLabel_1.setIcon(new ImageIcon("resources/red.png"));

            try {
                Thread.sleep(300);
            } catch (InterruptedException ex){}
            client.running = false;
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 163, 324, 488);
        getContentPane().add(scrollPane);

        list = new JList<>();
        list.setFixedCellHeight(80);
        list.setSelectedIndex(-1);

        list.setCellRenderer(new Friend());
        scrollPane.setViewportView(list);
        lm = new DefaultListModel<>();
        list.setModel(lm);

        list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                list.getSelectedValue().getChat().setVisible(true);
            }
        });
    }

    public void load (){
        client.running = true;
        while (true){
            while (client.running){
                try {
                    if (client.input.ready()){ //check for an incoming messge
                        String msg, uName, nName, sig, stus, timeStamp;
                        Friend frd;
                        boolean createNew;

                        msg = client.input.readLine(); //read the message
                        // parse in the friend list info from server
                        if (msg.equals("Friends List Info")){
                            msg = client.input.readLine(); //read the message
                            while (!msg.equals("End of Friends List")){
                                uName = msg;
                                nName = client.input.readLine();
                                sig = client.input.readLine();
                                stus = client.input.readLine();
                                msg = client.input.readLine();

                                createNew = true;
                                for (int i = 0; i < lm.getSize(); i++){
                                    frd = lm.get(i);

                                    if (uName.equals(frd.getUserName())){
                                        frd.setNickName(nName);
                                        frd.setSignature(sig);
                                        frd.setStatus(stus);
                                        frd.update();
                                        createNew = false;

//                                        if (frd.getStatus().equals("Online")){
//                                            lm.remove(i);
//                                            lm.add(0, frd);
//                                        }
                                        break;
                                    }
                                }
                                if (createNew){
                                    File chatHistory = new File("userfiles/" + this.username + "/" + uName + "_chat.txt");
                                    chatHistory.createNewFile();
                                    lm.addElement(new Friend(nName, sig, uName, stus, chatHistory, client));
                                }
                            }
                            this.repaint();
                            this.revalidate();

                        } else if (msg.equals("Message")){
                            msg = client.input.readLine(); //read the message
                            while (!msg.equals("End of Message")){
                                uName = msg;
                                nName = client.input.readLine();
                                timeStamp = client.input.readLine();
                                msg = client.input.readLine();

                                for (int i = 0; i < lm.getSize(); i++){
                                    frd = lm.get(i);
                                    if (uName.equals(frd.getUserName())){
                                        frd.receiveMessage((nName + " " + timeStamp), msg);
                                        break;
                                    }
                                }

                                msg = client.input.readLine();
                            }
                        } else {
                            System.out.print("Invalid command received from server");
                        }
                    }
                } catch (IOException e){
                    System.out.println("Failed to receive msg from the server");
                    e.printStackTrace();
                    client.close();
                }
            }
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
            }
        }
    }
}