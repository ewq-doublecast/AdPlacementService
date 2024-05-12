package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Deal;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.model.enums.PaymentMethod;
import com.example.adplacementservice.model.enums.Status;
import com.example.adplacementservice.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;

    public void setPaymentMethod(Integer dealId, PaymentMethod paymentMethod) {
        Deal deal = dealRepository.findById(dealId).orElseThrow();
        deal.setPaymentMethod(paymentMethod);
        dealRepository.save(deal);
    }

    public void closeDeal(Integer dealId, User user) {
        Deal deal = dealRepository.findById(dealId).orElseThrow();

        if (user.getEmail().equals(deal.getAd().getUser().getEmail()) && deal.getStatus().equals(Status.IN_PROCESS)) {
            deal.setStatus(Status.CLOSED);
            dealRepository.save(deal);
        }
    }

    public Deal getDeal(Integer id) {
        return dealRepository.findById(id).orElse(null);
    }
}
