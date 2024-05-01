package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;
    private final UserService userService;

    @GetMapping("/")
    public String ads(@RequestParam(name = "title", required = false) String title,
                      Model model,
                      Principal principal) {
        model.addAttribute("ads", adService.getAllAds(title));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "ads";
    }

    @GetMapping("/ad/{id}")
    public String ad(@PathVariable Integer id, Model model) {
        Ad ad = adService.getAd(id);
        model.addAttribute("ad", ad);
        model.addAttribute("images", ad.getImages());
        return "ad";
    }

    @GetMapping("/ad/create")
    public String create(Model model) {
        List<Category> categories = adService.getAllCategories();
        model.addAttribute("categories", categories);
        return "create-ad";
    }

    @PostMapping("/ad/create")
    public String createAd(@ModelAttribute Ad ad,
                           @RequestParam("file1") MultipartFile file1,
                           @RequestParam("file2") MultipartFile file2,
                           @RequestParam("file3") MultipartFile file3,
                           Principal principal) throws IOException {
        adService.save(ad, file1, file2, file3, principal);
        return "redirect:/";
    }

    @PostMapping("/ad/delete/{id}")
    public String deleteAd(@PathVariable int id) {
        adService.delete(id);
        return "redirect:/";
    }

}
