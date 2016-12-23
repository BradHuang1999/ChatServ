package networkingAssignment;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by Brad Huang on 12/21/2016.
 */
public class FriendListGUI extends JFrame{

    private String signature = "HelloWorld";
    private String nickname = "NICKNAME";
    private String username = "Username";
    private JTextField txtSignatureHelloworld;

    public FriendListGUI(ChatServ client){
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

        JLabel lblNickname = new JLabel(nickname);
        lblNickname.setFont(new Font("Impact", Font.PLAIN, 20));
        lblNickname.setBounds(10, 11, 171, 35);
        getContentPane().add(lblNickname);

        JLabel lblNewLabel = new JLabel("@ " + username);
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        lblNewLabel.setBounds(18, 52, 89, 14);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Online");
        lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));
        lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(240, 49, 77, 14);
        getContentPane().add(lblNewLabel_1);

        txtSignatureHelloworld = new JTextField();
        txtSignatureHelloworld.setText("Signature: " + signature);
        txtSignatureHelloworld.setBounds(10, 78, 220, 24);
        getContentPane().add(txtSignatureHelloworld);
        txtSignatureHelloworld.setColumns(10);

        //TODO figure out how to use documentlistener, check Chemical Balancer Project

        txtSignatureHelloworld.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                if (!txtSignatureHelloworld.getText().contains("Signature")){
                    txtSignatureHelloworld.setText("Signature: " + txtSignatureHelloworld.getText());
                }
                signature = txtSignatureHelloworld.getText().substring(11);
                client.output.println("ChangeSignature\n" + signature);
                client.output.flush();
            }
            public void removeUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {}
        });
//        txtSignatureHelloworld.addFocusListener(new FocusListener(){
//            public void focusLost(FocusEvent e){
//                signature = txtSignatureHelloworld.getText();
//                if (!txtSignatureHelloworld.getText().contains("Signature")){
//                    txtSignatureHelloworld.setText("Signature: " + txtSignatureHelloworld.getText());
//                }
//                signature = txtSignatureHelloworld.getText().substring(11);
//                client.output.println("ChangeSignature\n" + signature);
//            }
//
//            public void focusGained(FocusEvent e){
//                signature = txtSignatureHelloworld.getText();
//                if (!txtSignatureHelloworld.getText().contains("Signature")){
//                    txtSignatureHelloworld.setText("Signature: " + txtSignatureHelloworld.getText());
//                }
//                signature = txtSignatureHelloworld.getText().substring(11);
//                client.output.println("ChangeSignature\n" + signature);
//            }
//        });

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
            // TODO handle client.input, if needed, reopen connection
            lblNewLabel_1.setText("Online");
            lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));
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
        });

        JButton button_1 = new JButton("");
        ImageIcon im3 = new ImageIcon("resources/offline.png");
        button_1.setIcon(im3);
        button_1.setBounds(96, 122, 33, 23);
        getContentPane().add(button_1);
        button_1.addActionListener(e -> {
            client.output.println("Offline");
            client.output.flush();
            // TODO handle client.input information, sever connection temporarily without closing the window
            lblNewLabel_1.setText("Offline");
            lblNewLabel_1.setIcon(new ImageIcon("resources/red.png"));
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 163, 324, 488);
        getContentPane().add(scrollPane);

        JList<Friend> list = new JList<Friend>();
        list.setFixedCellHeight(80);
        list.setSelectedIndex(-1);

        list.setCellRenderer(new Friend());
        scrollPane.setViewportView(list);
        DefaultListModel lm = new DefaultListModel();
        list.setModel(lm);

        // TODO put the following code into a while loop, and update for client.input every second

        //JUST FOR TEST
        lm.addElement(new Friend("Friend 1 Nickname", "Friend 1 Signature", "Friend 1 UserName", "Friend 1 status"));
        lm.addElement(new Friend("Friend 2 Nickname", "Friend 2 Signature", "Friend 2 UserName", "Friend 2 status"));

        list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                client.output.println("ShowConvo\n" + list.getSelectedValue().getUserName());
                client.output.flush();

                ChatGUI chat = new ChatGUI(list.getSelectedValue().getNickName(), list.getSelectedValue().getUserName(), client);
                chat.setVisible(true);
            }
        });

        // TODO handle client.input information, setup While loop. Change signature, nickname, username, friendlist content accordingly
    }

    class Friend extends JPanel implements ListCellRenderer{
        private String nickName;
        private String userName;
        private String status;

        Friend(){
        }

        // TODO include status in the CellRenderer

        Friend(String name, String signature, String username, String status){
            this.nickName = name;
            this.userName = username;
            this.status = status;

            this.setLayout(null);

            this.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK)));
            JLabel lblNickname = new JLabel(name);
            lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNickname.setBounds(15, 15, 250, 35);

            JLabel lblMsn = new JLabel(signature);
            lblMsn.setFont(new Font("Courier New", Font.PLAIN, 16));
            lblMsn.setBounds(15, 40, 250, 35);
            this.add(lblNickname);
            this.add(lblMsn);
        }

        public String getNickName(){
            return this.nickName;
        }

        public String getUserName(){
            return this.userName;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            JPanel renderer = (JPanel)value;
            renderer.setBackground(isSelected ? Color.LIGHT_GRAY : list.getBackground());
            return renderer;
        }
    }
}