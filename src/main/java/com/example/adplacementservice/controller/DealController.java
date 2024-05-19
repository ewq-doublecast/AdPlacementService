package com.example.adplacementservice.controller;

import com.example.adplacementservice.dto.DealDto;
import com.example.adplacementservice.mapper.DealMapper;
import com.example.adplacementservice.model.Deal;
import com.example.adplacementservice.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;
    private final DealMapper dealMapper;

    @PostMapping("/deal/create")
    public String createDeal(@ModelAttribute DealDto dealDto) {
        Deal newDeal = dealMapper.toEntity(dealDto);
        dealService.save(newDeal);
        return "redirect:/ad/read/" + dealDto.getAdId();
    }

    @PostMapping("/deal/close/{id}")
    public String closeDeal(@PathVariable Integer id) {
        dealService.close(id);
        return "redirect:/ad/read/" + id;
    }

}
