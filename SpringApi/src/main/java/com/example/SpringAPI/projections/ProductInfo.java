package com.example.SpringAPI.projections;

import com.example.SpringAPI.models.Category;

/**
 * Projection for {@link com.example.SpringAPI.models.Product}
 */
public interface ProductInfo {
    String getTitle();

    int getPrice();

    String getDescription();

    Category getCategory();
}