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
}
