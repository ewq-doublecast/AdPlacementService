package com.example.adplacementservice.service;

import com.example.adplacementservice.model.User;
import com.example.adplacementservice.model.enums.Role;
import com.example.adplacementservice.repository.AdRepository;
import com.example.adplacementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public boolean save(User user) {
        String userEmail = user.getEmail();

        if (userRepository.findByEmail(userEmail) != null)
            return false;

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null)
            return false;

        user.setActive(true);
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new User with email: {}", userEmail);
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();

        return userRepository.findByEmail(principal.getName());
    }

}
