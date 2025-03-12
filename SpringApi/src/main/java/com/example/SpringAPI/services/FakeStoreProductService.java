package com.example.SpringAPI.services;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.FakeStoreProductDto;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FakeStoreProductService implements ProductService{
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(UUID id) throws ProductNotFoundException{
        FakeStoreProductDto productDto= restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        if(productDto==null){
            throw new ProductNotFoundException("Product Not Found",id);
        }
        return convertProductDtoToProduct(productDto);
    }

    private Product convertProductDtoToProduct(FakeStoreProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setId(productDto.getId());
        product.setDescription(productDto.getDescription());
        Category category = new Category();
        category.setTitle(productDto.getCategory());
        product.setCategory(category);

        return product;
    }

    private Category convertStringToCategory(String category) {
        Category category1 = new Category();
        category1.setTitle(category);
        return category1;
    }

    @Override
    public List<Product> getAllProducts(Map<String, String> filters) {
        FakeStoreProductDto[] productDtos= restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto productDto: productDtos){
            products.add(convertProductDtoToProduct(productDto));
        }
        return products;
    }

    @Override
    public List<Category> getAllCategories() {
        String[] categories= restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
        List<Category> categoriesList = new ArrayList<>();
        assert categories != null;
        for(String category: categories){
            categoriesList.add(convertStringToCategory(category));
        }
        return categoriesList;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        FakeStoreProductDto[] productDtos= restTemplate.getForObject("https://fakestoreapi.com/products/category/"+categoryName, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto productDto: productDtos){
            products.add(convertProductDtoToProduct(productDto));
        }

        return products;
    }

    @Override
    public ResponseEntity<Product> addProduct( ProductDto productDto) {
        FakeStoreProductDto fakeStoreProductDto= restTemplate.postForObject("https://fakestoreapi.com/products/",productDto, FakeStoreProductDto.class);
        Product product= convertProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Product> updateProduct(UUID id, ProductDto productDto) throws ProductNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(productDto, headers);

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreProductDto.class
        );
        Product product= convertProductDtoToProduct(response.getBody());
        if(product!=null){
            throw new ProductNotFoundException("Product Not Found",id);
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


}
