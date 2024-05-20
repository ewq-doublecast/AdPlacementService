package com.example.adplacementservice.specification;

import com.example.adplacementservice.model.Ad;
import org.springframework.data.jpa.domain.Specification;

public class AdSpecifications {
    public static Specification<Ad> hasCity(Integer cityId) {
        return (root, query, builder) -> cityId == null ? null : builder.equal(root.get("city").get("id"), cityId);
    }

    public static Specification<Ad> hasCategory(Integer categoryId) {
        return (root, query, builder) -> categoryId == null ? null : builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Ad> hasSearchText(String searchText) {
        return (root, query, builder) -> {
            if (searchText == null || searchText.isEmpty()) {
                return null;
            }
            String likePattern = "%" + searchText.toLowerCase() + "%";
            return builder.or(
                    builder.like(builder.lower(root.get("title")), likePattern),
                    builder.like(builder.lower(root.get("description")), likePattern),
                    builder.like(builder.lower(root.get("category").get("name")), likePattern),
                    builder.like(builder.lower(root.get("city").get("name")), likePattern)
            );
        };
    }

    public static Specification<Ad> hasNoDeal() {
        return (root, query, builder) -> builder.isNull(root.get("deal"));
    }
}
