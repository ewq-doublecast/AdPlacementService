package com.example.adplacementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private City city;

    @Column(name = "price")
    private Integer price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ad")
    private List<Image> images = new ArrayList<>();

    @Column(name = "preview_image_id")
    private Integer previewImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(name = "on_moderation")
    private boolean onModeration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "deal_id", referencedColumnName = "id")
    private Deal deal;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Review review;

    private boolean reviewWritten = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Delivery delivery;

    @PrePersist
    private void init() {
        createdAt = LocalDateTime.now();
    }

    public void addImage(Image image) {
        image.setAd(this);
        images.add(image);
    }

}
