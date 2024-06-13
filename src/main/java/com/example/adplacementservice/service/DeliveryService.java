package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Delivery;
import com.example.adplacementservice.model.enums.DeliveryStatus;
import com.example.adplacementservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void createDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllSentDeliveries(Integer senderId) {
        return deliveryRepository.findBySenderId(senderId);
    }

    public List<Delivery> getAllRecipientDeliveries(Integer recipientId) {
        return deliveryRepository.findByRecipientId(recipientId);
    }

    public void changeDeliveryStatus(Integer deliveryId, DeliveryStatus deliveryStatus) {
        Delivery delivery = deliveryRepository.getReferenceById(deliveryId);
        delivery.setDeliveryStatus(deliveryStatus);
        deliveryRepository.save(delivery);
    }

    public Delivery getDelivery(Integer deliveryId) {
        deliveryRepository.findById(deliveryId);
        return deliveryRepository.getReferenceById(deliveryId);
    }
}
