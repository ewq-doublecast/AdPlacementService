package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByAdId(Integer adId);
}
