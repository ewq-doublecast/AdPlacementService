package com.example.adplacementservice.service;

import com.example.adplacementservice.dto.ReviewDto;
import com.example.adplacementservice.mapper.ReviewMapper;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        review.setCreatedAt(LocalDateTime.now());
        review.setOnModeration(true);
        review.getAd().setReview(review);
        review.getAd().setReviewWritten(true);
        reviewRepository.save(review);
    }

    public List<Review> getReviewsOnModeration() {
        return reviewRepository.findByOnModerationIsTrue();
    }

    public void approve(Integer id) {
        Review reviewFromDb = reviewRepository.getReferenceById(id);
        reviewFromDb.setOnModeration(false);
        userService.calculateAndSetUserAvgRating(reviewFromDb.getSeller());
        reviewRepository.save(reviewFromDb);
    }

    public List<Review> getUserReviews(Integer userId) {
        return reviewRepository.findBySellerId(userId);
    }
}
