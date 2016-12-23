package networkingAssignment;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Brad Huang on 12/23/2016.
 */
public class SignupGUI extends JFrame{

    private JPanel panel1;

    public SignupGUI(ChatServ client){
        getContentPane().setLayout(null);

        setTitle("Login");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("ChatServ Sign Up");
        title.setBounds(160, 25, 120, 14);
        getContentPane().add(title);

        JLabel lblNewLabel = new JLabel("Username:");
        lblNewLabel.setBounds(65, 110, 120, 14);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nickname:");
        lblNewLabel_1.setBounds(65, 142, 120, 14);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password:");
        lblNewLabel_2.setBounds(65, 174, 120, 14);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Repeat Password:");
        lblNewLabel_3.setBounds(65, 206, 120, 14);
        getContentPane().add(lblNewLabel_3);

        JTextField textField = new JTextField();
        textField.setBounds(185, 107, 140, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        JTextField textFieldNick = new JTextField();
        textFieldNick.setBounds(185, 139, 140, 20);
        getContentPane().add(textFieldNick);
        textFieldNick.setColumns(10);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(185, 171, 140, 20);
        getContentPane().add(passwordField);

        JPasswordField passwordField2 = new JPasswordField();
        passwordField2.setBounds(185, 203, 140, 20);
        getContentPane().add(passwordField2);

        JLabel warning = new JLabel("");
        warning.setForeground(Color.RED);
        warning.setBounds(111, 78, 198, 22);
        getContentPane().add(warning);

        JButton btnSignup = new JButton("Sign Up!");
        btnSignup.addActionListener(e -> {
            warning.setText("");

            client.output.println("CreateUser\n" + textField.getText() + "\n" + textFieldNick.getText() + "\n" + String.valueOf(passwordField.getPassword()) + "\n" + String.valueOf(passwordField2.getPassword()));
            client.output.flush();

            boolean run = true;
            while (run){
                try {
                    if (client.input.ready()){ //check for an incoming messge
                        String msg;
                        msg = client.input.readLine(); //read the message
                        if (msg.equals("User created")){
                            dispose();
                        } else {
                            System.out.println("Server to Signup: " + msg);
                            warning.setText(msg);
                        }
                        run = false;
                    }
                } catch (IOException ex){
                    System.out.println("Failed to receive msg from the server");
                    ex.printStackTrace();
                    client.close();
                }
            }
        });
        btnSignup.setBounds(89, 235, 89, 23);
        getContentPane().add(btnSignup);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            dispose();
        });
        btnCancel.setBounds(222, 235, 89, 23);
        getContentPane().add(btnCancel);


    }
}
