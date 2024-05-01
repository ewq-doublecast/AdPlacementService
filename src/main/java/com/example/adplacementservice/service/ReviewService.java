package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Ad;
import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.repository.AdRepository;
import com.example.adplacementservice.repository.ReviewRepository;
import com.example.adplacementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public void save(Integer id, Review review, User user) {
        Optional<Ad> ad = adRepository.findById(id);

        if (ad.isPresent()) {
            User author = userRepository.findByEmail(user.getEmail());
            review.setAuthor(author);
            review.setAd(ad.get());
            ad.get().setReview(review);
            ad.get().setReviewHasBeenWritten(true);
        }

        reviewRepository.save(review);
    }

    public void deleteReviewById(int id) {
        reviewRepository.deleteById(id);
    }


}
