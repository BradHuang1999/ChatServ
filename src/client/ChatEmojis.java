package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Brad Huang on 12/21/2016.
 */
public class ChatEmojis extends JFrame{
    public static String selectedEmoji;

    public ChatEmojis(){
        getContentPane().setLayout(new GridLayout(0, 4, 0, 0));

        setTitle("");
        setSize(100, 120);
        setResizable(false);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ActionListener ac = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JButton source = (JButton)e.getSource();
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

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

