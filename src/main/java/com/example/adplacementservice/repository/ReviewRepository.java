package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByOnModerationIsTrue();
}
