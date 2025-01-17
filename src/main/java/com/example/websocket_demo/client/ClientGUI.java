package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame {
    private JPanel connectedUsersPanel,messagePanel;

    public ClientGUI(String username){
        super("User: " + username);

        setSize(1218,685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               int option = JOptionPane.showConfirmDialog( ClientGUI.this,"Do you really want to leave?","Exit",JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    ClientGUI.this.dispose();
                }
            }
        });

        getContentPane().setBackground(Utilities.primaryColor);
        addGuiComponents();
    }
    private void addGuiComponents(){
        addConntectedUsersComponents();
        addChatComponents();
    }
    private void addConntectedUsersComponents(){
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setBorder(Utilities.addPadding(10,10,10,10));
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.secondaryColor);
        connectedUsersPanel.setPreferredSize(new Dimension(200,getHeight()));

        JLabel connectedUsersLabel = new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter",Font.BOLD,18));
        connectedUsersLabel.setForeground(Utilities.textColor);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel,BorderLayout.WEST);

    }

    private void addChatComponents(){
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Utilities.transparentColor);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.Y_AXIS));
        messagePanel.setBackground(Utilities.transparentColor);
        chatPanel.add(messagePanel, BorderLayout.CENTER);

        messagePanel.add(createChatMessageConponent(new Message("Kenny","Hello world!")));

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(Utilities.addPadding(10,10,10,10));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.transparentColor);

        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    String input = inputField.getText();

                    //edge case: empty message(prevent empty messages)
                    if (input.isEmpty()) return;

                    inputField.setText("");

                    messagePanel.add(createChatMessageConponent(new Message("Kenny",input)));
                    repaint();
                    revalidate();
                }
            }
        });
        inputField.setBackground(Utilities.secondaryColor);
        inputField.setForeground(Utilities.textColor);
        inputField.setFont(new Font("Inter",Font.PLAIN,16));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(),50));
        inputPanel.add(inputField,BorderLayout.CENTER);
        chatPanel.add(inputField,BorderLayout.SOUTH);


        add(chatPanel,BorderLayout.CENTER);
    }
    private JPanel createChatMessageConponent(Message message){
        JPanel chatMessage = new JPanel();
        chatMessage.setBackground(Utilities.transparentColor);
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBorder(Utilities.addPadding(20,20,10,20));

        JLabel usernameLabel = new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.BOLD,18));
        usernameLabel.setForeground(Utilities.textColor);
        chatMessage.add(usernameLabel);

        JLabel messageLabel = new JLabel(message.getMessage());
        messageLabel.setFont(new Font("Inter",Font.PLAIN,18));
        messageLabel.setForeground(Utilities.textColor);
        chatMessage.add(messageLabel);

        return chatMessage;
    }
}
