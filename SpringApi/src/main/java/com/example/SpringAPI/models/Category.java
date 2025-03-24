package com.example.SpringAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name="Categories")
public class Category extends BaseClass implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Category(String category) {
        this.title = category;
    }

    public Category() {

    }
}
