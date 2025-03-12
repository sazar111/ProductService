package com.example.SpringAPI.specifications;

import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class ProductSpecification {

    public static Specification<Product> hasField(String field, String value) {

        return (root, query, criteriaBuilder) -> {
            if (value.contains("%")) {
                // Use LIKE if the value contains a wildcard character (%)
                return criteriaBuilder.like(root.get(field), value);
            } else {
                // Use EQUALS otherwise | optimization
                return criteriaBuilder.equal(root.get(field), value);
            }
        };
    }

    public static Specification<Product> hasPriceGreaterThanOrEqualTo(Integer minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasPriceLessThanOrEqualTo(Integer maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

}
