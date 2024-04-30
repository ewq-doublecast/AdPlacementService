package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Ad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdService {

    private List<Ad> ads = new ArrayList<>();

    private int id = 0;

    {
        ads.add(new Ad(++id, "Good 1", "Simple Description", "Rostov-on-Don", 10000, "Nikolai"));
        ads.add(new Ad(++id, "Good 2", "Simple Description", "Rostov-on-Don", 10000, "Nikolai"));
        ads.add(new Ad(++id, "Good 3", "Simple Description", "Rostov-on-Don", 10000, "Nikolai"));
    }

    public void save(Ad ad) {
        ad.setId(++id);
        ads.add(ad);
    }

    public List<Ad> getAllAds() {
        return ads;
    }

    public Ad getAd(int id) {
        for (Ad ad : ads) {
            if (ad.getId() == id)
                return ad;
        }

        return null;
    }

    public void delete(int id) {
        ads.removeIf(ad -> ad.getId() == id);
    }

}
