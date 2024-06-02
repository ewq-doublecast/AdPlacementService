package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Chat;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.repository.ChatRepository;
import com.example.adplacementservice.repository.UserRepository;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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
        model.addAttribute("chat", chat);
        model.addAttribute("currentUser", user);
        return "chat";
    }
}
