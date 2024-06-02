package com.example.adplacementservice.service;

import com.example.adplacementservice.dto.ReviewDto;
import com.example.adplacementservice.mapper.ReviewMapper;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    public void save(ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        review.getSeller().addSellerReview(review);
        review.getAuthor().addAuthorReview(review);
        userService.calculateAndSetUserAvgRating(review.getSeller());
        review.setCreatedAt(LocalDateTime.now());
        review.setOnModeration(true);
        review.getAd().setReview(review);
        review.getAd().setReviewWritten(true);
        reviewRepository.save(review);
    }

}
