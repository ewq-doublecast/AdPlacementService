package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @GetMapping("/")
    public String ads(Model model) {
        model.addAttribute("ads", adService.getAllAds());
        return "ads";
    }

    @GetMapping("/ad/{id}")
    public String ad(@PathVariable int id, Model model) {
        model.addAttribute("ad", adService.getAd(id));
        return "ad";
    }

    @GetMapping("/ad/create")
    public String create() {
        return "create-ad";
    }

    @PostMapping("/ad/create")
    public String createAd(@ModelAttribute Ad ad) {
        adService.save(ad);
        return "redirect:/";
    }

    @PostMapping("/ad/delete/{id}")
    public String deleteAd(@PathVariable int id) {
        adService.delete(id);
        return "redirect:/";
    }

}
