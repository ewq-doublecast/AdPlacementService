package com.example.adplacementservice.repository;

import com.example.adplacementservice.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findByOnModerationIsTrue();

    List<Ad> findByDealIdIsNull();

    @Query("SELECT a FROM Ad a WHERE a.deal.id IN :dealIds")
    List<Ad> findByDealIds(@Param("dealIds") List<Integer> dealIds);

}
