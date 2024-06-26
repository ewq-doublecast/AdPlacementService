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
        newDelivery.setDeliveryStatus(DeliveryStatus.IN_PROCESS_SENDING);
        deliveryService.createDelivery(newDelivery);
        return "redirect:/delivery/sent-deliveries/" + newDelivery.getSender().getId();
    }

    @GetMapping("/delivery/sent-deliveries/{userId}")
    public String viewSentDeliveries(@PathVariable Integer userId, Model model) {
        List<Delivery> sentDeliveries = deliveryService.getAllSentDeliveries(userId);
        model.addAttribute("sentDeliveries", sentDeliveries);
        String status = "";
        for (Delivery sentDelivery : sentDeliveries) {
            switch (sentDelivery.getDeliveryStatus()) {
                case IN_PROCESS_SENDING:
                    status = "В процессе отправки";
                    break;
                case ON_THE_WAY:
                    status = "В пути";
                    break;
                case RECEIVED:
                    status = "Получено";
                    break;
            }
        }
        model.addAttribute("deliveryStatus", status);
        return "sent-deliveries";
    }

    @GetMapping("/delivery/recipient-deliveries/{userId}")
    public String viewRecipientDeliveries(@PathVariable Integer userId, Model model) {
        List<Delivery> recipientDeliveries = deliveryService.getAllRecipientDeliveries(userId);
        model.addAttribute("recipientDeliveries", recipientDeliveries);
        String status = "";
        for (Delivery recipientDelivery : recipientDeliveries) {
            switch (recipientDelivery.getDeliveryStatus()) {
                case IN_PROCESS_SENDING:
                    status = "В процессе отправки";
                    break;
                case ON_THE_WAY:
                    status = "В пути";
                    break;
                case RECEIVED:
                    status = "Получено";
                    break;
            }
        }
        model.addAttribute("deliveryStatus", status);
        return "recipient-deliveries";
    }

    @PostMapping("/delivery/change-status/{deliveryId}")
    public String changeDeliveryStatus(@PathVariable Integer deliveryId, DeliveryStatus deliveryStatus) {
        deliveryService.changeDeliveryStatus(deliveryId, deliveryStatus);
        return "redirect:/delivery/deliveries";
    }

    @GetMapping("/delivery/{deliveryId}")
    public String viewDelivery(@PathVariable Integer deliveryId, Model model) {
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        model.addAttribute("delivery", delivery);
        return "delivery";
    }

}
