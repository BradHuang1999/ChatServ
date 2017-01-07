package client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class ChatGUI extends JFrame{

    JTextPane textArea;

	public ChatGUI(Friend friend, ChatServ client) throws IOException{
        Scanner chIn = new Scanner(friend.getChatHistory());
        PrintWriter chOut = new PrintWriter(new FileWriter(friend.getChatHistory(), true));

		getContentPane().setLayout(null);

		setTitle("Chat with " + friend.getNickName());
		setSize(400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
        addWindowListener(new WindowListener(){
            public void windowClosed(WindowEvent e){}
            public void windowOpened(WindowEvent e){}
            public void windowClosing(WindowEvent e){
                chOut.close();
                dispose();
            }
            public void windowIconified(WindowEvent e){}
            public void windowDeiconified(WindowEvent e){}
            public void windowActivated(WindowEvent e){}
            public void windowDeactivated(WindowEvent e){}
        });

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 394, 350);
		getContentPane().add(scrollPane);

		textArea = new JTextPane();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setFocusable(false);
		scrollPane.setViewportView(textArea);

        String line;
        while (chIn.hasNextLine()){
            line = chIn.nextLine();
            if (line.substring(0, 3).equals("Me ")){
                appendToPane(line, Color.BLUE);
                appendToPane(chIn.nextLine(), Color.BLACK);
            } else {
                appendToPane(line, Color.DARK_GRAY);
                appendToPane(chIn.nextLine(), Color.BLACK);
            }
        }
        chIn.close();

		JButton btnNewButton = new JButton("Emojis");
		btnNewButton.setBounds(10, 361, 89, 23);
		btnNewButton.addActionListener(e -> {
			ChatEmojis chatEmojis = new ChatEmojis();
			chatEmojis.addWindowListener(new WindowListener(){
				public void windowClosed(WindowEvent e){
					if (chatEmojis.selectedEmoji == null){
						return;
					}

					client.output.println("SendMessage\n" + friend.getUserName() + "\n" + new Date() + "\n//emoji//-" + chatEmojis.selectedEmoji);
                    client.output.flush();

                    chOut.println("Me " + new Date() + "\n//emoji//-" + chatEmojis.selectedEmoji);

					appendToPane("Me " + new Date(), Color.BLUE);
					appendToPane("//emoji//-" + chatEmojis.selectedEmoji, Color.BLACK);

					chatEmojis.selectedEmoji = null;
				}
				public void windowOpened(WindowEvent e){}
				public void windowClosing(WindowEvent e){}
				public void windowIconified(WindowEvent e){}
				public void windowDeiconified(WindowEvent e){}
				public void windowActivated(WindowEvent e){}
				public void windowDeactivated(WindowEvent e){}
			});
			chatEmojis.setLocationRelativeTo(btnNewButton);
			chatEmojis.setVisible(true);
		});
		getContentPane().add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 395, 299, 56);
		getContentPane().add(scrollPane_1);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setRows(2);
		scrollPane_1.setViewportView(textArea_1);

		JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(109, 361, 275, 22);
		getContentPane().add(lblError);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(319, 396, 65, 55);
		getContentPane().add(btnSend);
		btnSend.addActionListener(e -> {
			if (textArea_1.getText().isEmpty()){
				lblError.setText("No text to send");
				return;
			} else {
				lblError.setText("");
			}

			client.output.println("SendMessage\n" + friend.getUserName() + "\n" + new Date() + "\n" + textArea_1.getText());
            client.output.flush();

            String msg = textArea_1.getText();

			appendToPane("Me " + new Date(), Color.BLUE);
			appendToPane(msg, Color.BLACK);
			textArea_1.setText("");

            chOut.println("Me " + new Date());
            chOut.println(msg);
		});
    }

    public void appendToPane(String msg, Color color){
        if (msg.length() < 10 || (!msg.substring(0, 10).equals("//emoji//-"))){
            msg = msg + "\n";
            appendToPane(textArea, msg, color);
        } else {
            appendToPane(textArea, " ", color);
            textArea.insertIcon(new ImageIcon(msg.substring(10)));
            appendToPane(textArea, "\n", color);
        }
    }

	private void appendToPane(JTextPane tp, String msg, Color c){
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
}