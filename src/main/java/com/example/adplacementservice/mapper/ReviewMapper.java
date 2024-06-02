package com.example.adplacementservice.mapper;

import com.example.adplacementservice.dto.ReviewDto;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.service.AdService;
import com.example.adplacementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final AdService adService;
    private final UserService userService;

    public Review toEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setAd(adService.getAd(reviewDto.getAdId()));
        review.setSeller(userService.getUserById(reviewDto.getSellerId()));
        review.setAuthor(userService.getUserById(reviewDto.getAuthorId()));
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        return review;
    }

    public ReviewDto toDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setAdId(review.getAd().getId());
        reviewDto.setAuthorId(review.getAuthor().getId());
        reviewDto.setSellerId(review.getSeller().getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setDescription(review.getDescription());
        return reviewDto;
    }
}
