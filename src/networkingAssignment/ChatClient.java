package networkingAssignment;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ChatClient extends JFrame{
	public ChatClient(String friendNick) {
		getContentPane().setLayout(null);
		
		setTitle("Chat with " + friendNick);
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
		
		if(friendNick != null){
			appendToPane(textArea, friendNick +" "+ new Date() , Color.DARK_GRAY);
			appendToPane(textArea, "\n Hey ... " , Color.BLACK);
		}
		
		JButton btnNewButton = new JButton("Emojis");
		btnNewButton.setBounds(10, 361, 89, 23);
		btnNewButton.addActionListener(e ->{
			ChatEmojis chatEmojis = new ChatEmojis();
			chatEmojis.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {}
				
				@Override
				public void windowIconified(WindowEvent e) {}
				
				@Override
				public void windowDeiconified(WindowEvent e) {}
				
				@Override
				public void windowDeactivated(WindowEvent e) {}
				
				@Override
				public void windowClosing(WindowEvent e) {}
				
				@Override
				public void windowClosed(WindowEvent e) {
					if(chatEmojis.selectedEmoji == null){
						return;
					}
					appendToPane(textArea, "\nMe " + new Date() , Color.BLUE);
					appendToPane(textArea, "\n " , Color.BLUE);
					textArea.insertIcon ( new ImageIcon(chatEmojis.selectedEmoji) );//.setVisible(true);
					chatEmojis.selectedEmoji = null;
				}
				
				@Override
				public void windowActivated(WindowEvent e) {}
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
		btnSend.addActionListener(e ->{
			if(textArea_1.getText().isEmpty()){
				lblError.setText("None text to send");
				return;
			}else{
				lblError.setText("");
			}
			appendToPane(textArea, "\nMe " + new Date() , Color.BLUE);
			appendToPane(textArea, "\n "+textArea_1.getText() , Color.BLACK);
			textArea_1.setText("");
		});
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
	
	public static void main(String[] args) {
		new LoginGUI().setVisible(true);
	}
}

class ChatEmojis extends JFrame{
	public static String selectedEmoji;
	public ChatEmojis(){
		getContentPane().setLayout(new GridLayout(0, 4, 0, 0));
		
		setTitle("");
		setSize(100, 120);
		setResizable(false);
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ActionListener ac = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				selectedEmoji = source.getName();
				dispose();
			}
		};
		
		try {
			JButton btnNewButton = new JButton("");
			String pa = "resources/emojis/emojiAngel.png";
			btnNewButton.setName(pa);
			BufferedImage read = ImageIO.read(new File(pa));
			Image scaled = read.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton.setIcon(new ImageIcon(scaled));
			getContentPane().add(btnNewButton);
			btnNewButton.addActionListener(ac);
			
			JButton btnNewButton_1 = new JButton("");
			String pa1 = "resources/emojis/emojiClap.png";
			btnNewButton_1.setName(pa1);
			BufferedImage read1 = ImageIO.read(new File(pa1));
			Image scaled1 = read1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_1.setIcon(new ImageIcon(scaled1));
			getContentPane().add(btnNewButton_1);
			btnNewButton_1.addActionListener(ac);
			
			JButton btnNewButton_2 = new JButton("");
			String pa2 = "resources/emojis/emojiCool.png";
			btnNewButton_2.setName(pa2);
			BufferedImage read2 = ImageIO.read(new File(pa2));
			Image scaled2 = read2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_2.setIcon(new ImageIcon(scaled2));
			getContentPane().add(btnNewButton_2);
			btnNewButton_2.addActionListener(ac);
			
			JButton btnNewButton_3 = new JButton("");
			String pa3 = "resources/emojis/emojiCry.png";
			btnNewButton_3.setName(pa3);
			BufferedImage read3 = ImageIO.read(new File(pa3));
			Image scaled3 = read3.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_3.setIcon(new ImageIcon(scaled3));
			getContentPane().add(btnNewButton_3);
			btnNewButton_3.addActionListener(ac);
			
			JButton btnNewButton_4 = new JButton("");
			String pa4 = "resources/emojis/emojiDevil.png";
			btnNewButton_4.setName(pa4);
			BufferedImage read4 = ImageIO.read(new File(pa4));
			Image scaled4 = read4.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_4.setIcon(new ImageIcon(scaled4));
			getContentPane().add(btnNewButton_4);
			btnNewButton_4.addActionListener(ac);
			
			JButton btnNewButton_5 = new JButton("");
			String pa5 = "resources/emojis/emojiFun.png";
			btnNewButton_5.setName(pa5);
			BufferedImage read5 = ImageIO.read(new File(pa5));
			Image scaled5 = read5.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_5.setIcon(new ImageIcon(scaled5));
			getContentPane().add(btnNewButton_5);
			btnNewButton_5.addActionListener(ac);
			
			JButton btnNewButton_6 = new JButton("");
			String pa6 = "resources/emojis/emojiHaha.png";
			btnNewButton_6.setName(pa6);
			BufferedImage read6 = ImageIO.read(new File(pa6));
			Image scaled6 = read6.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_6.setIcon(new ImageIcon(scaled6));
			getContentPane().add(btnNewButton_6);
			btnNewButton_6.addActionListener(ac);
			
			JButton btnNewButton_7 = new JButton("");
			String pa7 = "resources/emojis/emojiHeart.png";
			btnNewButton_7.setName(pa7);
			BufferedImage read7 = ImageIO.read(new File(pa7));
			Image scaled7 = read7.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_7.setIcon(new ImageIcon(scaled7));
			getContentPane().add(btnNewButton_7);
			btnNewButton_7.addActionListener(ac);
			
			JButton btnNewButton_8 = new JButton("");
			String pa8 = "resources/emojis/emojiLaugh.png";
			btnNewButton_8.setName(pa8);
			BufferedImage read8 = ImageIO.read(new File(pa8));
			Image scaled8 = read8.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_8.setIcon(new ImageIcon(scaled8));
			getContentPane().add(btnNewButton_8);
			btnNewButton_8.addActionListener(ac);
			
			JButton btnNewButton_9 = new JButton("");
			String pa9 = "resources/emojis/emojiLol.png";
			btnNewButton_9.setName(pa9);
			BufferedImage read9 = ImageIO.read(new File(pa9));
			Image scaled9 = read9.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_9.setIcon(new ImageIcon(scaled9));
			getContentPane().add(btnNewButton_9);
			btnNewButton_9.addActionListener(ac);
			
			JButton btnNewButton_10 = new JButton("");
			String pa10 = "resources/emojis/emojiOoo.png";
			btnNewButton_10.setName(pa10);
			BufferedImage read10 = ImageIO.read(new File(pa10));
			Image scaled10 = read10.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_10.setIcon(new ImageIcon(scaled10));
			getContentPane().add(btnNewButton_10);
			btnNewButton_10.addActionListener(ac);
			
			JButton btnNewButton_11 = new JButton("");
			String pa11 = "resources/emojis/emojiSad.png";
			btnNewButton_11.setName(pa11);
			BufferedImage read11 = ImageIO.read(new File(pa11));
			Image scaled11 = read11.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_11.setIcon(new ImageIcon(scaled11));
			getContentPane().add(btnNewButton_11);
			btnNewButton_11.addActionListener(ac);
			
			JButton btnNewButton_12 = new JButton("");
			String pa12 = "resources/emojis/emojiSmirk.png";
			btnNewButton_12.setName(pa12);
			BufferedImage read12 = ImageIO.read(new File(pa12));
			Image scaled12 = read12.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_12.setIcon(new ImageIcon(scaled12));
			getContentPane().add(btnNewButton_12);
			btnNewButton_12.addActionListener(ac);
			
			JButton btnNewButton_13 = new JButton("");
			String pa13 = "resources/emojis/emojiSnore.png";
			btnNewButton_13.setName(pa13);
			BufferedImage read13 = ImageIO.read(new File(pa13));
			Image scaled13 = read13.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_13.setIcon(new ImageIcon(scaled13));
			getContentPane().add(btnNewButton_13);
			btnNewButton_13.addActionListener(ac);
			
			JButton btnNewButton_14 = new JButton("");
			String pa14 = "resources/emojis/emojiThumbsUp.png";
			btnNewButton_14.setName(pa14);
			BufferedImage read14 = ImageIO.read(new File(pa14));
			Image scaled14 = read14.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_14.setIcon(new ImageIcon(scaled14));
			getContentPane().add(btnNewButton_14);
			btnNewButton_14.addActionListener(ac);
			
			JButton btnNewButton_15 = new JButton("");
			String pa15 = "resources/emojis/emojiZip.png";
			btnNewButton_15.setName(pa15);
			BufferedImage read15 = ImageIO.read(new File(pa15));
			Image scaled15 = read15.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btnNewButton_15.setIcon(new ImageIcon(scaled15));
			getContentPane().add(btnNewButton_15);
			btnNewButton_15.addActionListener(ac);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class FriendListGUI extends JFrame{
	private String signature = "HelloWorld";
	private String nickname = "NICKNAME";
	private String username = "Username";
	private JTextField txtSignatureHelloworld;
	public FriendListGUI() {
		getContentPane().setLayout(null);
		setTitle("ChatServ Friend List");
		
		setSize(350, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel lblNickname = new JLabel(nickname);
		lblNickname.setFont(new Font("Impact", Font.PLAIN, 20));
		lblNickname.setBounds(10, 11, 171, 35);
		getContentPane().add(lblNickname);
		
		JLabel lblNewLabel = new JLabel("@ "+username);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 57, 89, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Online");
		lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(257, 58, 77, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtSignatureHelloworld = new JTextField();
		txtSignatureHelloworld.setText("Signature: "+signature);
		txtSignatureHelloworld.setBounds(80, 82, 180, 20);
		getContentPane().add(txtSignatureHelloworld);
		txtSignatureHelloworld.setColumns(10);
		txtSignatureHelloworld.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				signature = txtSignatureHelloworld.getText();
				if(!txtSignatureHelloworld.getText().contains("Signature")){
					txtSignatureHelloworld.setText("Signature : " +txtSignatureHelloworld.getText() );					
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
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
		btnNewButton.addActionListener(e ->{
			lblNewLabel_1.setText("Online");
			lblNewLabel_1.setIcon(new ImageIcon("resources/green.png"));
		});
		
		JButton button = new JButton("");
		ImageIcon im2 = new ImageIcon("resources/busy.png");
		button.setIcon(im2);
		button.setBounds(53, 122, 33, 23);
		getContentPane().add(button);
		button.addActionListener(e ->{
			lblNewLabel_1.setText("Busy");
			lblNewLabel_1.setIcon(new ImageIcon("resources/red.png"));
		});
		
		JButton button_1 = new JButton("");
		ImageIcon im3 = new ImageIcon("resources/offline.png");
		button_1.setIcon(im3);
		button_1.setBounds(96, 122, 33, 23);
		getContentPane().add(button_1);
		button_1.addActionListener(e ->{
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
		
		//JUST FOR TEST
		lm.addElement(new Friend("Friend 1 Nickname", "Friend 1 Message"));
		lm.addElement(new Friend("Friend 2 Nickname", "Friend 2 Message"));
		
		list.getSelectionModel().addListSelectionListener(e ->{
			if (e.getValueIsAdjusting() == false) {
				new ChatClient(list.getSelectedValue().getNickName()).setVisible(true);
			}
		});
	}
	
	class Friend extends JPanel implements ListCellRenderer{
		
		private String nickName;

		Friend(){}
		
		Friend(String name, String message){
			this.nickName = name;
			this.setLayout(null);
			
			this.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), BorderFactory.createLineBorder(Color.BLACK)));
			JLabel lblNickname = new JLabel(name);
			lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblNickname.setBounds(15, 15, 250, 35);
			
			JLabel lblMsn = new JLabel(message);
			lblMsn.setFont(new Font("Courier New", Font.PLAIN, 17));
			lblMsn.setBounds(15, 40, 250, 35);
			this.add(lblNickname);
			this.add(lblMsn);
		}
		
		public String getNickName(){
			return this.nickName;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JPanel renderer = (JPanel) value;
			renderer.setBackground(isSelected ? Color.LIGHT_GRAY : list.getBackground());
	        return renderer;
		}
	}
}

class LoginGUI extends JFrame{
	private JTextField textField;
	private JPasswordField passwordField;
	
	public LoginGUI() {
		getContentPane().setLayout(null);
		
		setTitle("Login");
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		lblNewLabel_2.setBounds(94, 11, 198, 155);
		getContentPane().add(lblNewLabel_2);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(e ->{
			new FriendListGUI().setVisible(true);
			this.dispose();
		});
		btnLogin.setBounds(50, 235, 89, 23);
		getContentPane().add(btnLogin);

		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e ->{
			System.exit(0);
		});
		btnCancel.setBounds(150, 235, 89, 23);
		getContentPane().add(btnCancel);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(e ->{
			//TODO: create the action for sign up button
		});
		btnSignUp.setBounds(250, 235, 89, 23);
		getContentPane().add(btnSignUp);
	}
}
