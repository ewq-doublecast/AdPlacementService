package com.example.adplacementservice.controller;

import com.example.adplacementservice.dto.MessageDto;
import com.example.adplacementservice.model.Chat;
import com.example.adplacementservice.model.Message;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.repository.ChatRepository;
import com.example.adplacementservice.repository.MessageRepository;
import com.example.adplacementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDto sendMessage(MessageDto messageDTO) {
        Chat chat = chatRepository.findById(messageDTO.getChatId()).orElseThrow();
        User sender = userRepository.findById(messageDTO.getSenderId()).orElseThrow();
        User recipient = userRepository.findById(messageDTO.getRecipientId()).orElseThrow();
        Message message = new Message(null, chat, sender, recipient, messageDTO.getContent(), LocalDateTime.now());
        messageRepository.save(message);
        return messageDTO;
    }
}