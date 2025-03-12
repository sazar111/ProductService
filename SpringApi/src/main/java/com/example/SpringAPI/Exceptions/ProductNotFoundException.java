package com.example.SpringAPI.Exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductNotFoundException extends Exception{
    UUID id;

    public ProductNotFoundException(String errorMessage, UUID id){

        super(errorMessage);
        this.id= id;
    }
}
