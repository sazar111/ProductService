package com.example.SpringAPI.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "Products")
public class Product extends BaseClass implements Serializable  {
    String title;
    int price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    Category category;
    String description;
    String image;
    String  ratings;
}
