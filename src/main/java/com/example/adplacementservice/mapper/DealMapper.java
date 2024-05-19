package com.example.adplacementservice.mapper;

import com.example.adplacementservice.dto.DealDto;
import com.example.adplacementservice.model.Deal;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DealMapper {

    private final AdService adService;
    private final UserService userService;

    public Deal toEntity(DealDto dealDto) {
        Deal deal = new Deal();
        deal.setAd(adService.getAd(dealDto.getAdId()));
        deal.setSeller(userService.getUserById(dealDto.getSellerId()));
        deal.setBuyer(userService.getUserById(dealDto.getBuyerId()));
        deal.setPaymentMethod(dealDto.getPaymentMethod());
        return deal;
    }

    public DealDto toDto(Deal deal) {
        if (deal == null) {
            return null;
        }
        DealDto dealDto = new DealDto();
        dealDto.setAdId(deal.getAd().getId());
        dealDto.setSellerId(deal.getSeller().getId());
        dealDto.setBuyerId(deal.getBuyer().getId());
        dealDto.setPaymentMethod(deal.getPaymentMethod());
        return dealDto;
    }
}
