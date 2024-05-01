package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

}
