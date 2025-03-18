package com.example.SpringAPI.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FakeStoreProductDto {
    UUID id;
    String title;
    int price;
    String category;
    String description;
    String image;



    // Parameterized constructor
    public FakeStoreProductDto(UUID id, String title, int price, String category, String description, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
    }
}
