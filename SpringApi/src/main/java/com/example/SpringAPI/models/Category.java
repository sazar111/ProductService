package com.example.SpringAPI.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="Categories")
public class Category extends BaseClass{
    private String title;

}
