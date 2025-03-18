package com.example.SpringAPI.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name="Categories")
public class Category extends BaseClass{
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Category(String category) {
        this.title = category;
    }

    public Category() {

    }
}
