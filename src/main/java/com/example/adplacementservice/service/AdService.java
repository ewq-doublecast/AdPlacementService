package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    public void save(Ad ad) {
        log.info("Ad saved to database: {}", ad);
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

}

