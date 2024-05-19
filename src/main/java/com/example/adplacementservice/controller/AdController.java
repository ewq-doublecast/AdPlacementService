package com.example.adplacementservice.controller;

import com.example.adplacementservice.dto.DealDto;
import com.example.adplacementservice.mapper.DealMapper;
import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.model.City;
import com.example.adplacementservice.model.enums.DealStatus;
import com.example.adplacementservice.service.*;
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
    private final CityService cityService;
    private final CategoryService categoryService;
    private final DealService dealService;
    private final DealMapper dealMapper;

    @GetMapping("/")
    public String readAllAds(@RequestParam(name = "title", required = false) String title,
                             Model model,
                             Principal principal) {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("ads", adService.getAdsWhereDealIsNull());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "read-all-ads";
    }

    @GetMapping("/ad/read/{id}")
    public String readAd(@PathVariable Integer id, Model model, Principal principal) {
        Ad ad = adService.getAd(id);

        DealDto dealDto = dealMapper.toDto(dealService.getDealByAdId(id));

        if (dealDto == null) {
            dealDto = new DealDto();
            dealDto.setAdId(id);
            dealDto.setSellerId(ad.getUser().getId());

            if (principal != null) {
                dealDto.setBuyerId(userService.getUserByPrincipal(principal).getId());
            }
        }

        model.addAttribute("ad", ad);
        model.addAttribute("images", ad.getImages());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("dealDto", dealDto);
        model.addAttribute("dealStatus", DealStatus.values());
        return "read-ad";
    }

    @GetMapping("/ad/create")
    public String create(Model model) {
        Ad ad = new Ad();
        List<Category> categories = adService.getAllCategories();
        List<City> cities = cityService.getAllCities();
        model.addAttribute("categories", categories);
        model.addAttribute("cities", cities);
        model.addAttribute("ad", ad);
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

    @GetMapping("/ad/edit/{id}")
    public String updateAd(Model model, @PathVariable Integer id, Principal principal) {
        Ad ad = adService.getAd(id);
        List<Category> categories = adService.getAllCategories();
        List<City> cities = cityService.getAllCities();
        model.addAttribute("ad", ad);
        model.addAttribute("categories", categories);
        model.addAttribute("cities", cities);
        model.addAttribute("images", ad.getImages());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "update-ad";
    }

    @PostMapping("/ad/edit")
    public String updateAd(@ModelAttribute Ad ad,
                           @RequestParam("file1") MultipartFile file1,
                           @RequestParam("file2") MultipartFile file2,
                           @RequestParam("file3") MultipartFile file3,
                           Principal principal) throws IOException {
        adService.update(ad, file1, file2, file3, principal);
        return "redirect:/ad/read/" + ad.getId();
    }

    @PostMapping("/ad/delete/{id}")
    public String deleteAd(@PathVariable int id) {
        adService.delete(id);
        return "redirect:/";
    }

}
