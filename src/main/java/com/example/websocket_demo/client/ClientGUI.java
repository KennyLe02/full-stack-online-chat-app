package com.example.websocket_demo.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame {
    private JPanel connectedUsersPanel;

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
    }
    private void addConntectedUsersComponents(){
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.secondaryColor);
        connectedUsersPanel.setPreferredSize(new Dimension(200,getHeight()));

        JLabel connectedUsersLabel = new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter",Font.BOLD,18));
        connectedUsersLabel.setForeground(Utilities.textColor);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel,BorderLayout.WEST);

    }
}
