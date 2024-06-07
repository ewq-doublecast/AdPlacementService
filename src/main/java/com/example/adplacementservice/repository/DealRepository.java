package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer> {

    Deal findByAdId(Integer id);

    List<Deal> findByBuyerId(Integer userId);

}
