package com.example.adplacementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description")
    private String description;

    @Column(name = "ceated_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn
    private User author;

    @ManyToOne
    @JoinColumn
    private User seller;

    @OneToOne
    private Ad ad;

    @Column(name = "on_moderation")
    private boolean onModeration;

}
