package com.example.websocket_demo.client;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        //This makes updates more threadsafe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI clientGUI = new ClientGUI("Kenny");
                clientGUI.setVisible(true);
            }
        });
    }
}
