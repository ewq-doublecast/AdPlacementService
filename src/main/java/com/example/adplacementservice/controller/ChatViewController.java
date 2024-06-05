package com.example.adplacementservice.controller;

import com.example.adplacementservice.dto.MessageDto;
import com.example.adplacementservice.model.Chat;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.repository.ChatRepository;
import com.example.adplacementservice.repository.UserRepository;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ChatController chatController;

    @GetMapping("/chats")
    public String getChats(Model model, Principal principal) {
        User currentUser = userService.getUserByPrincipal(principal);
        User user = userRepository.findByEmail(currentUser.getUsername());
        List<Chat> chats = chatRepository.findAll().stream()
                .filter(chat -> chat.getParticipants().contains(user))
                .collect(Collectors.toList());
        model.addAttribute("chats", chats);
        model.addAttribute("currentUser", user);
        return "chats";
    }

    @GetMapping("/chats/{chatId}")
    public String getChat(@PathVariable Integer chatId, Model model, Principal principal) {
        User currentUser = userService.getUserByPrincipal(principal);
        User user = userRepository.findByEmail(currentUser.getUsername());
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        Predicate<User> isRecipient = x -> !x.getEmail().equals(currentUser.getUsername());
        User recipient = chat.getParticipants().stream().filter(isRecipient).findFirst().get();
        model.addAttribute("chat", chat);
        model.addAttribute("recipient", recipient);
        model.addAttribute("currentUser", user);
        return "chat";
    }

    @PostMapping("/chat/send-first-message")
    public String sendFirstMessage(@ModelAttribute MessageDto messageDto) {
        Chat chat = new Chat();
        Set<User> users = new HashSet<>();
        User sender = userRepository.findById(messageDto.getSenderId()).orElseThrow();
        User recipient = userRepository.findById(messageDto.getRecipientId()).orElseThrow();
        users.add(sender);
        users.add(recipient);
        chat.setParticipants(users);
        chatRepository.save(chat);
        Chat chatFromDb = chatRepository.findById(chat.getId()).orElseThrow();
        messageDto.setChatId(chatFromDb.getId());
        chatController.sendMessage(messageDto);
        return "redirect:/chats";
    }
}
