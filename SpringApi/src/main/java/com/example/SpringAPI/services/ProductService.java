package com.example.SpringAPI.services;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Product getProductById(UUID id) throws ProductNotFoundException;
    Page<Product> getAllProducts(Map<String, String> filters,int page, int size, String sortBy);

    List<Category> getAllCategories();

    List<Product> getProductsByCategory(String categoryName);

    ResponseEntity<Product> addProduct( ProductDto productDto) throws ProductNotFoundException;

    ResponseEntity<Product> updateProduct(UUID id, ProductDto productDto) throws ProductNotFoundException;
}
