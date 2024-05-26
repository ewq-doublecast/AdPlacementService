package com.example.adplacementservice.controller;

import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.model.City;
import com.example.adplacementservice.service.CategoryService;
import com.example.adplacementservice.service.CityService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalController {

    private final UserService userService;
    private final CityService cityService;
    private final CategoryService categoryService;

    @ModelAttribute("allCities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @ModelAttribute("allCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @ModelAttribute
    public void addUserToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            Integer userId = getUserIdByUsername(username);
            model.addAttribute("currentUserId", userId);
        }
    }

    private Integer getUserIdByUsername(String username) {
        return userService.getUserByEmail(username).getId();
    }

}
