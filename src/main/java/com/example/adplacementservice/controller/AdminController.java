package com.example.adplacementservice.controller;

import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AdService adService;
    private final ReviewService reviewService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("ads", adService.getAdsOnModeration());
        model.addAttribute("reviews", reviewService.getReviewsOnModeration());
        return "admin";
    }

    @PostMapping("/admin/ad/approve/{id}")
    public String approveAd(@PathVariable Integer id) {
        adService.approve(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/review/approve/{id}")
    public String approveReview(@PathVariable Integer id) {
        reviewService.approve(id);
        return "redirect:/admin";
    }


}
