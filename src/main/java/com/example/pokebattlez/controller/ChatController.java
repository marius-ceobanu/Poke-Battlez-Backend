package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.request.ChatMessage;
import com.example.pokebattlez.model.request.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    private static final String USER = "Prof. Oak"; // TODO Replace with username

    private final OnlineUsers onlineUsers;
    private final SimpMessagingTemplate socket;

    @Autowired
    public ChatController(OnlineUsers onlineUsers, SimpMessagingTemplate socket) {
        this.onlineUsers = onlineUsers;
        this.socket = socket;
    }

    @CrossOrigin
    @MessageMapping("/message/lobby")
    @SendTo("/chat/lobby")
    public ChatMessage lobbyChat(UserMessage userMessage) {
        System.out.println(onlineUsers.getUsers());
//        socket.convertAndSend("/chat/lobby", new ChatMessage(USER, HtmlUtils.htmlEscape(userMessage.getBody())));
        return new ChatMessage(USER, HtmlUtils.htmlEscape(userMessage.getBody()));
    }

    @MessageMapping("/message/battle/{id}")
    @SendTo("/chat/battle/{id}")
    public ChatMessage battleChat(UserMessage userMessage, @DestinationVariable int id) {
        return new ChatMessage(USER, HtmlUtils.htmlEscape(userMessage.getBody()));
    }
}
