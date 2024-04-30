package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findByTitle(String title);

}
