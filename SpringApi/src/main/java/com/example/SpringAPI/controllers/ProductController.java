package com.example.SpringAPI.controllers;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.Exceptions.UnauthorizedException;
import com.example.SpringAPI.commons.AuthCommon;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.dtos.TokenRequestDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import com.example.SpringAPI.services.ProductService;
import com.example.SpringAPI.services.SelfProductService;
import lombok.experimental.Delegate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Controller
public class ProductController {

    ProductService productService;
    AuthCommon authCommon;

    ProductController( @Qualifier("selfProductService") ProductService productService, AuthCommon authCommon) {
        this.productService = productService;
        this.authCommon = authCommon;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") UUID id) throws ProductNotFoundException, UnauthorizedException {

        return productService.getProductById(id);
    }

    @GetMapping("")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam Map<String, String> filters, // Collects all query parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        //return ResponseEntity.ok(getAllProducts(filters));
        Page<Product> products= productService.getAllProducts(filters,page,size,sortBy);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable("categoryName")  String category){
        return productService.getProductsByCategory(category);
    }

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product) throws ProductNotFoundException{
        return productService.addProduct( product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto product, @PathVariable("id") UUID id) throws ProductNotFoundException {
        return productService.updateProduct(id, product);
    }

}

