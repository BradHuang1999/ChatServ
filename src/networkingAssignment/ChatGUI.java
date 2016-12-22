package networkingAssignment;

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
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;

public class ChatGUI extends JFrame{
	public ChatGUI(String friendNickName, String friendUserName, PrintWriter output, BufferedReader input, ChatServ client){
		getContentPane().setLayout(null);

		setTitle("Chat with " + friendNickName);
		setSize(400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 394, 350);
		getContentPane().add(scrollPane);

		JTextPane textArea = new JTextPane();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setFocusable(false);
		scrollPane.setViewportView(textArea);

		if (friendNickName != null){
			appendToPane(textArea, friendNickName + " " + new Date(), Color.DARK_GRAY);
			appendToPane(textArea, "\n Hey ... ", Color.BLACK);
		}   // for now...

		JButton btnNewButton = new JButton("Emojis");
		btnNewButton.setBounds(10, 361, 89, 23);
		btnNewButton.addActionListener(e -> {
			ChatEmojis chatEmojis = new ChatEmojis();
			chatEmojis.addWindowListener(new WindowListener(){
				public void windowClosed(WindowEvent e){
					if (chatEmojis.selectedEmoji == null){
						return;
					}

					output.println("SendEmoji\n" + friendUserName + "\n" + new Date() + "\n" + chatEmojis.selectedEmoji);
                    output.flush();

					appendToPane(textArea, "\nMe " + new Date(), Color.BLUE);
					appendToPane(textArea, "\n ", Color.BLUE);
					textArea.insertIcon(new ImageIcon(chatEmojis.selectedEmoji));
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

			output.println("SendMessage\n" + friendUserName + "\n" + new Date() + "\n" + textArea_1.getText());
            output.flush();

			appendToPane(textArea, "\nMe " + new Date(), Color.BLUE);
			appendToPane(textArea, "\n " + textArea_1.getText(), Color.BLACK);
			textArea_1.setText("");
		});

		// TODO set up While loop to receive message
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