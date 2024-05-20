package com.example.adplacementservice.service;

import com.example.adplacementservice.mapper.ImageMapper;
import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Category;
import com.example.adplacementservice.model.Image;
import com.example.adplacementservice.repository.AdRepository;
import com.example.adplacementservice.repository.CategoryRepository;
import com.example.adplacementservice.repository.ImageRepository;
import com.example.adplacementservice.specification.AdSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final ImageMapper imageMapper;

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

        Ad adFromDb = adRepository.save(ad);
        adFromDb.setPreviewImageId(adFromDb.getImages().get(0).getId());
        adRepository.save(adFromDb);
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
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

    public List<Ad> getAdsWhereDealIsNull(String searchText, Integer cityId, Integer categoryId) {
        Specification<Ad> spec = Specification.where(AdSpecifications.hasSearchText(searchText))
                .and(AdSpecifications.hasCity(cityId))
                .and(AdSpecifications.hasCategory(categoryId))
                .and(AdSpecifications.hasNoDeal());

        return adRepository.findAll(spec);
    }

    public List<Ad> getAdsByDealIds(List<Integer> dealIds) {
        return adRepository.findByDealIds(dealIds);
    }

    public void update(Ad updatedAd,
                       MultipartFile file1,
                       MultipartFile file2,
                       MultipartFile file3,
                       Principal principal) throws IOException {

        Ad adFromDb = adRepository.findById(updatedAd.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ad not found with id: " + updatedAd.getId()));

        if (!adFromDb.getUser().getUsername().equals(principal.getName())) {
            return;
        }

        adFromDb.setTitle(updatedAd.getTitle());
        adFromDb.setDescription(updatedAd.getDescription());
        adFromDb.setCity(updatedAd.getCity());
        adFromDb.setPrice(updatedAd.getPrice());
        adFromDb.setCategory(updatedAd.getCategory());

        List<Image> existingImages = imageRepository.findByAdId(updatedAd.getId());

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

    private void updateImage(List<Image> existingImages, int index, Image image) {
        Image existingImage = existingImages.get(index);
        existingImage.setName(image.getName());
        existingImage.setOriginalFileName(image.getOriginalFileName());
        existingImage.setSize(image.getSize());
        existingImage.setContentType(image.getContentType());
        existingImage.setBytes(image.getBytes());
        imageRepository.save(existingImage);
    }


}

