package com.example.adplacementservice.controller;

import com.example.adplacementservice.dto.ReviewDto;
import com.example.adplacementservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/create")
    public String create(@ModelAttribute ReviewDto reviewDto) {
        reviewService.save(reviewDto);
        return "redirect:/ad/read/" + reviewDto.getAdId();
    }

}
