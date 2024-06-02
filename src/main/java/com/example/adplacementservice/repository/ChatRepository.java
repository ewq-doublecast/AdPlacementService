package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
