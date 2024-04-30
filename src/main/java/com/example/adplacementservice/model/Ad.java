package com.example.adplacementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ad {
    private Integer id;
    private String title;
    private String description;
    private String city;
    private Integer price;
    private String author;
}
