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
}
