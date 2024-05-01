package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.ReviewService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final AdService adService;
    private final UserService userService;

    @GetMapping("/review/create/{adId}")
    public String createReview(@PathVariable Integer adId, @ModelAttribute Ad ad, Model model) {
        model.addAttribute("ad", adService.getAd(adId));
        return "review-create";
    }

    @PostMapping("/review/create/{adId}")
    public String createReview(@PathVariable Integer adId, @ModelAttribute Review review, Principal principal) {
        reviewService.save(adId, review, userService.getUserByPrincipal(principal));
        return "redirect:/";
    }

}
