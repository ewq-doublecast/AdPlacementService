package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}