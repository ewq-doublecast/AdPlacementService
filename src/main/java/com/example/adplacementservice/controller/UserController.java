package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.model.City;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.service.CategoryService;
import com.example.adplacementservice.service.CityService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CityService cityService;
    private final CategoryService categoryService;

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
    public String user(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("ads", user.getAds());
        return "user";
    }

    @ModelAttribute("allCities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @ModelAttribute("allCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
