package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Deal;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.DealService;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AdService adService;
    private final DealService dealService;
    private final ReviewService reviewService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, Model model) {
        if (!userService.save(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String user(@PathVariable User user, Model model, Principal principal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<Review> reviews = reviewService.getUserReviews(user.getId());
        model.addAttribute("formatter", formatter);
        model.addAttribute("user", user);
        model.addAttribute("ads", user.getAds());
        model.addAttribute("reviews", reviews);
        User guest = userService.getUserByPrincipal(principal);
        if (guest.getEmail().equals(user.getEmail())) {
            List<Deal> deals = dealService.getAllPurchasedDeals(guest.getId());
            List<Integer> dealIds = new ArrayList<>();
            for (Deal deal : deals) {
                dealIds.add(deal.getId());
            }
            model.addAttribute("purchasedAds", adService.getAdsByDealIds(dealIds));
        } else {
            model.addAttribute("purchasedAds", null);
        }

        return "user";
    }

}
