package com.example.SpringAPI.dtos;

import com.example.SpringAPI.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    UUID id;
    String title;
    int price;
    Category category;
    String description;
    String image;


    public ProductDto(String title,  int price, String category, String description, String image) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.category = new Category(category);
        this.description = description;
        this.image = image;
    }

    public ProductDto() {

    }
}
