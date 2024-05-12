package com.example.adplacementservice.controller;

import com.example.adplacementservice.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdService adService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("ads", adService.getAdsOnModeration());
        return "admin";
    }

    @PostMapping("/admin/ad/approve/{id}")
    public String approveAd(@PathVariable int id) {
        adService.approve(id);
        return "redirect:/admin";
    }

}
