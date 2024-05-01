package com.example.adplacementservice.service;

import com.example.adplacementservice.mapper.ImageMapper;
import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.model.Image;
import com.example.adplacementservice.repository.AdRepository;
import com.example.adplacementservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final ImageMapper imageMapper;

    private final UserService userService;

    public void save(Ad ad,
                     MultipartFile file1,
                     MultipartFile file2,
                     MultipartFile file3,
                     Principal principal) throws IOException {

        ad.setUser(userService.getUserByPrincipal(principal));

        Image image1;
        Image image2;
        Image image3;

        if (file1.getSize() != 0) {
            image1 = imageMapper.toEntity(file1);
            image1.setPreviewImage(true);
            ad.addImage(image1);
        }

        if (file2.getSize() != 0) {
            image2 = imageMapper.toEntity(file2);
            ad.addImage(image2);
        }

        if (file3.getSize() != 0) {
            image3 = imageMapper.toEntity(file3);
            ad.addImage(image3);
        }

        ad.setOnModeration(true);
        log.info("Saving new Ad. Title: {}; Author: {}", ad.getTitle(), ad.getUser().getFirstName());

        Ad adFromDb = adRepository.save(ad);
        adFromDb.setPreviewImageId(adFromDb.getImages().get(0).getId());
        adRepository.save(ad);
    }


    public List<Ad> getAllAds(String title) {
        if (title != null)
            return adRepository.findByTitle(title);

        return adRepository.findAll();
    }

    public Ad getAd(int id) {
        return adRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        adRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}

