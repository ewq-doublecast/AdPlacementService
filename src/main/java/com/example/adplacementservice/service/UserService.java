package com.example.adplacementservice.service;

import com.example.adplacementservice.model.Review;
import com.example.adplacementservice.model.User;
import com.example.adplacementservice.model.enums.Role;
import com.example.adplacementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean save(User user) {
        String userEmail = user.getEmail();

        if (userRepository.findByEmail(userEmail) != null)
            return false;

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null)
            return false;

        user.getRoles().add(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();

        return userRepository.findByEmail(principal.getName());
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void calculateAndSetUserAvgRating(User user) {
        List<Review> userReviews = user.getMyReviews();
        if (userReviews != null) {
            Double rating = 0.0;
            for (Review review : userReviews) {
                rating += review.getRating();
            }
            user.setAvgRating(rating / userReviews.size());
        }
    }
}
