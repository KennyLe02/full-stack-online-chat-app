package com.example.websocket_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

//makes this class as the mvc controller. It'll handle the web requests
@Controller
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebsocketSessionManager sessionManager;

    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate,WebsocketSessionManager sessionManager){
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/message")
    public void handleMessage(Message message){
        System.out.println("Received message from user: " + message.getUser() + ": " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages",message);
        System.out.println("Sent message to /topic/messages: " + message.getUser() + ":" + message.getMessage());
    }
    @MessageMapping("/connect")
    public void connectUser(String username){
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username + " connected");
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username){
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username+ " disconnect");
    }
}
