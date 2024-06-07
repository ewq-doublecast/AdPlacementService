package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    List<Delivery> findBySenderId(Integer userId);

    List<Delivery> findByRecipientId(Integer recipientId);
}
