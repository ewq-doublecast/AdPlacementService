package com.example.adplacementservice.service;

import com.example.adplacementservice.model.User;
import com.example.adplacementservice.model.enums.Role;
import com.example.adplacementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
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
        user.getRoles().add(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new User with email: {}", userEmail);
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void banUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Banned user with id: {}", id);
            } else {
                user.setActive(true);
                log.info("Unbanned user with id: {}", id);
            }
        }

        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

}
