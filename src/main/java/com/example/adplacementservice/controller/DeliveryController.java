package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Delivery;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.model.enums.DeliveryStatus;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final AdService adService;

    @GetMapping("/delivery/create")
    public String viewCreateDelivery(Model model, @RequestParam Integer adId) {
        Ad ad = adService.getAd(adId);
        Delivery delivery = new Delivery();
        model.addAttribute("delivery", delivery);
        model.addAttribute("ad", ad);
        return "create-delivery";
    }

    @GetMapping("/delivery/deliveries")
    public String viewDeliveries() {
        return "deliveries";
    }

    @PostMapping("/delivery/create")
    public String createDelivery(@ModelAttribute Delivery delivery, @RequestParam Integer adId) {
        Delivery newDelivery = new Delivery();
        newDelivery.setSenderAddress(delivery.getSenderAddress());
        newDelivery.setSenderIndex(delivery.getSenderIndex());
        newDelivery.setRecipientAddress(delivery.getRecipientAddress());
        newDelivery.setRecipientIndex(delivery.getRecipientIndex());
        Ad ad = adService.getAd(adId);
        newDelivery.setAd(ad);
        User sender = ad.getUser();
        newDelivery.setSender(sender);
        User recipient = ad.getDeal().getBuyer();
        newDelivery.setRecipient(recipient);
        newDelivery.setStatus(DeliveryStatus.IN_PROCESS_SENDING);
        deliveryService.createDelivery(newDelivery);
        return "redirect:/delivery/sent-deliveries/" + newDelivery.getSender().getId();
    }

    @GetMapping("/delivery/sent-deliveries/{userId}")
    public String viewSentDeliveries(@PathVariable Integer userId, Model model) {
        List<Delivery> sentDeliveries = deliveryService.getAllSentDeliveries(userId);
        model.addAttribute("sentDeliveries", sentDeliveries);
        return "sent-deliveries";
    }

    @GetMapping("/delivery/recipient-deliveries/{userId}")
    public String viewRecipientDeliveries(@PathVariable Integer userId, Model model) {
        List<Delivery> recipientDeliveries = deliveryService.getAllRecipientDeliveries(userId);
        model.addAttribute("recipientDeliveries", recipientDeliveries);
        return "recipient-deliveries";
    }

    @PostMapping("/delivery/change-status/{deliveryId}")
    public String changeDeliveryStatus(@PathVariable Integer deliveryId, DeliveryStatus deliveryStatus) {
        deliveryService.changeDeliveryStatus(deliveryId, deliveryStatus);
        return "redirect:/delivery/deliveries";
    }

}
