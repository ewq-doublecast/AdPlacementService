package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Deal;
import com.example.adplacementservice.model.enums.DealStatus;
import com.example.adplacementservice.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;

    public void save(Deal deal) {
        deal.setStatus(DealStatus.IN_PROCESS);
        deal.getAd().setDeal(deal);
        dealRepository.save(deal);
    }

    public Deal getDealByAdId(Integer adId) {
        return dealRepository.findByAdId(adId);
    }

    public List<Deal> getAllPurchasedDeals(Integer buyerId) {
        return dealRepository.findByBuyerId(buyerId);
    }

    public void close(Integer id) {
        Deal dealFromDb = dealRepository.findByAdId(id);

        if (dealFromDb != null) {
            dealFromDb.setStatus(DealStatus.CLOSED);
            dealRepository.save(dealFromDb);
        }
    }

    public void cancel(Integer id) {
        Optional<Deal> dealFromDb = dealRepository.findById(id);

        if (dealFromDb.isPresent()) {
            dealFromDb.get().setStatus(DealStatus.CANCEL);
            dealRepository.save(dealFromDb.get());
        }
    }

}
