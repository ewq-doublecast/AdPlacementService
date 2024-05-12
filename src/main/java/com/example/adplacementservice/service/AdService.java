package com.example.adplacementservice.service;

import com.example.adplacementservice.mapper.ImageMapper;
import com.example.adplacementservice.model.*;
import com.example.adplacementservice.model.enums.Status;
import com.example.adplacementservice.repository.AdRepository;
import com.example.adplacementservice.repository.CategoryRepository;
import com.example.adplacementservice.repository.DealRepository;
import com.example.adplacementservice.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final ImageMapper imageMapper;
    private final DealRepository dealRepository;

    private final UserService userService;
    private final ImageRepository imageRepository;

    @Transactional
    public void save(Ad ad,
                     MultipartFile file1,
                     MultipartFile file2,
                     MultipartFile file3,
                     Principal principal) throws IOException {

        ad.setUser(userService.getUserByPrincipal(principal));

        if (file1.getSize() != 0) {
            Image image1 = imageMapper.toEntity(file1);
            image1.setPreviewImage(true);
            ad.addImage(image1);
        }

        if (file2.getSize() != 0) {
            Image image2 = imageMapper.toEntity(file2);
            ad.addImage(image2);
        }

        if (file3.getSize() != 0) {
            Image image3 = imageMapper.toEntity(file3);
            ad.addImage(image3);
        }

        ad.setOnModeration(true);

        Deal deal = new Deal();
        deal.setAd(ad);
        deal.setSeller(ad.getUser());
        deal.setStatus(Status.ACTIVE);
        dealRepository.save(deal);

        Ad adFromDb = adRepository.save(ad);
        adFromDb.setPreviewImageId(adFromDb.getImages().get(0).getId());
        adRepository.save(adFromDb);
    }

    public List<Ad> getAllAds(String title) {
        if (title != null)
            return adRepository.findByTitle(title);

        List<Ad> ads = new ArrayList<>();

        List<Ad> allAds = adRepository.findAll();

        for (Ad ad : allAds) {
            if (ad.getDeal().getStatus() == Status.ACTIVE) {
                ads.add(ad);
            }
        }

        return ads;
    }

    public Ad getAd(int id) {
        return adRepository.findById(id).orElse(null);
    }

    public void approve(int id) {
        Ad adFromDb = getAd(id);
        adFromDb.setOnModeration(false);
        adRepository.save(adFromDb);
    }

    public void delete(int id) {
        adRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Ad> getAdsOnModeration() {
        return adRepository.findByOnModerationIsTrue();
    }

    public void update(Integer adId, Ad updatedAd,
                       MultipartFile file1,
                       MultipartFile file2,
                       MultipartFile file3,
                       Principal principal) throws IOException {

        Ad adFromDb = adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found with id: " + adId));

        if (!adFromDb.getUser().getUsername().equals(principal.getName())) {
            return;
        }

        adFromDb.setTitle(updatedAd.getTitle());
        adFromDb.setDescription(updatedAd.getDescription());
        adFromDb.setCity(updatedAd.getCity());
        adFromDb.setPrice(updatedAd.getPrice());
        adFromDb.setCategory(updatedAd.getCategory());

        List<Image> existingImages = imageRepository.findByAdId(adId);

        if (file1 != null && file1.getSize() > 0) {
            Image image1 = imageMapper.toEntity(file1);
            image1.setPreviewImage(true);
            if (!existingImages.isEmpty()) {
                updateImage(existingImages, 0, image1);
            } else {
                adFromDb.addImage(image1);
            }
        }

        if (file2 != null && file2.getSize() > 0) {
            Image image2 = imageMapper.toEntity(file2);
            if (existingImages.size() > 1) {
                updateImage(existingImages, 1, image2);
            } else {
                adFromDb.addImage(image2);
            }
        }

        if (file3 != null && file3.getSize() > 0) {
            Image image3 = imageMapper.toEntity(file3);
            if (existingImages.size() > 2) {
                updateImage(existingImages, 2, image3);
            } else {
                adFromDb.addImage(image3);
            }
        }

        adFromDb.setOnModeration(true);
        adRepository.save(adFromDb);
    }

    private void updateImage(List<Image> existingImages, int index, Image image1) {
        Image existingImage = existingImages.get(index);
        existingImage.setName(image1.getName());
        existingImage.setOriginalFileName(image1.getOriginalFileName());
        existingImage.setSize(image1.getSize());
        existingImage.setContentType(image1.getContentType());
        existingImage.setBytes(image1.getBytes());
        imageRepository.save(existingImage);
    }

    @Transactional
    public void buyAd(Ad ad, User buyer) {
        Ad adFromDb = adRepository.findById(ad.getId()).orElseThrow();
        Deal deal = dealRepository.findById(adFromDb.getDeal().getId()).orElseThrow();

        if (!adFromDb.getUser().getUsername().equals(buyer.getUsername())) {
            deal.setBuyer(buyer);
            deal.setPaymentMethod(ad.getDeal().getPaymentMethod());
            deal.setStatus(Status.IN_PROCESS);
            dealRepository.save(deal);
        }
    }

}

