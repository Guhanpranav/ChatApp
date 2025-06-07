package com.example.chatapp.Controller;

import com.example.chatapp.Entity.ChatMessage;
import com.example.chatapp.Repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRepo cRepo;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        message.setTimestamp(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        cRepo.save(message);
        return message;
    }

    @MessageMapping("/addUser")
    public void addUser(@Payload ChatMessage message) {
        message.setContent(message.getSender() + " joined the chat");
        message.setType("JOIN");
        messagingTemplate.convertAndSend("/topic/public", message);
    }
}
