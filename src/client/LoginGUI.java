package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * LogIn UI
 * @author Brad Huang, Charlie Lin
 */
public class LoginGUI extends JFrame {

    // GUI variables
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * constructor
     *
     * @param client    controls I/O
     */
    public LoginGUI(ChatServ client) {
        getContentPane().setLayout(null);

        setTitle("Login");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                client.close();
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

        textField = new JTextField();
        textField.setBounds(179, 175, 125, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Username:");
        lblNewLabel.setBounds(104, 175, 65, 14);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Password:");
        lblNewLabel_1.setBounds(104, 209, 65, 14);
        getContentPane().add(lblNewLabel_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(179, 206, 125, 20);
        getContentPane().add(passwordField);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("resources/IMG_001.png"));
        lblNewLabel_2.setBounds(98, 11, 198, 155);
        getContentPane().add(lblNewLabel_2);

        JLabel warning = new JLabel("");
        warning.setForeground(Color.RED);
        warning.setBounds(111, 147, 198, 22);
        getContentPane().add(warning);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            warning.setText("");

            client.output.println("Login\n" + textField.getText() + "\n" + String.valueOf(passwordField.getPassword()));
            client.output.flush();

            boolean successful = false;     // successful

            client.running = true;      // start receiving message
            while (client.running) {
                try {
                    if (client.input.ready()) {      //check for an incoming messge
                        String msg;
                        msg = client.input.readLine();      //read the message
                        if (msg.equals("Successful")) {
                            client.friendList = new FriendListGUI(client, client.input.readLine(), client.input.readLine(), client.input.readLine());
                            client.friendList.setVisible(true);     // if successful, set up and display the friendlist UI
                            this.dispose();     // close the login UI
                            successful = true;
                        } else {
                            warning.setText("Invalid Username or Password");        // set up warning
                        }
                        client.running = false;     // stop receiving messages
                    }
                } catch (IOException ex) {
                    System.out.println("Failed to receive msg from the server");
                    ex.printStackTrace();
                    client.close();
                }
            }
            if (successful) {
                try {
                    Thread.sleep(550);      // pause the thread to receive any extra message from server
                } catch (InterruptedException e1) {
                }

                new Thread(() ->
                        client.friendList.load()
                ).start();
            }
        });
        btnLogin.setBounds(50, 235, 89, 23);
        getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> client.close());
        btnCancel.setBounds(150, 235, 89, 23);
        getContentPane().add(btnCancel);

        JButton btnSignUp = new JButton("Sign up");
        btnSignUp.addActionListener(e -> {
            SignupGUI signup = new SignupGUI(client);       // setup and display a signup UI
            signup.setVisible(true);
        });
        btnSignUp.setBounds(250, 235, 89, 23);
        getContentPane().add(btnSignUp);
    }
}