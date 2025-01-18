package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientGUI extends JFrame implements MessageListener{
    private JPanel connectedUsersPanel,messagePanel;
    private MyStompClient myStompClient;
    private String username;


    public ClientGUI(String username) throws ExecutionException, InterruptedException {
        super("User: " + username);
        this.username = username;
        myStompClient = new MyStompClient(this, username);

        setSize(1218,685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               int option = JOptionPane.showConfirmDialog( ClientGUI.this,"Do you really want to leave?","Exit",JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    myStompClient.disconnectUser(username);
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


                    myStompClient.sendMessage(new Message(username, input));

                }
            }
        });
        inputField.setBackground(Utilities.secondaryColor);
        inputField.setForeground(Utilities.textColor);
        inputField.setBorder(Utilities.addPadding(0,10,0,10));
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
   @Override
   public void onMessageReceive(Message message){
       messagePanel.add(createChatMessageConponent(message));
       revalidate();
       repaint();
    }
    @Override
    public void onActiveUsersUpdated(ArrayList<String> users){

        if (connectedUsersPanel.getComponents().length >= 2){
            connectedUsersPanel.remove(1);
        }

        JPanel userListPanel = new JPanel();
        userListPanel.setBackground(Utilities.transparentColor);
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        for(String user: users){
            JLabel username = new JLabel();
            username.setText(user);
            username.setForeground(Utilities.textColor);
            username.setFont(new Font("Inter",Font.BOLD,16));
            userListPanel.add(username);
        }
        connectedUsersPanel.add(userListPanel);
        revalidate();
        repaint();
    }
}
