package com.example.SpringAPI.services;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import com.example.SpringAPI.repositories.CategoryRepository;
import com.example.SpringAPI.repositories.ProductRepository;
import com.example.SpringAPI.specifications.ProductSpecification;
import com.example.SpringAPI.specifications.SpecificationBuilder;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service
public class SelfProductService implements ProductService {

    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;

    ProductCacheService productCacheService;

    public SelfProductService(ProductRepository productRepository,CategoryRepository categoryRepository,ProductCacheService productCacheService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCacheService = productCacheService;
    }

    @Override
    public Product getProductById(UUID id) throws ProductNotFoundException {
        Optional<Product> product= productCacheService.getProductFromCache(id);
        if(!product.isPresent()){
            product= productRepository.findById(id);
            if(product.isPresent()){
                productCacheService.saveProduct(product.get());
            }
        }

        if (product.isEmpty()) {
            throw new ProductNotFoundException("No Product with id "+id,id);
        }
        return product.get();
    }

    @Override
    public Page<Product> getAllProducts(Map<String, String> filters, int page, int size, String sortBy) {

        Specification<Product> spec = filters.entrySet().stream()
                .map(entry -> ProductSpecification.hasField(entry.getKey(), entry.getValue()))
                .reduce(Specification::and)
                .orElse(null);  // If no filters, pass `null`

        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(sortBy));

        return productRepository.findAll(spec, pageable);  // ✅ Returns Page<Product>
    }


    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        Category category= new Category();
        category.setTitle(categoryName);
        return productRepository.getProductsByCategory(category);
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        //product.setId((long) productDto.getId());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());

        return product;
    }

    private Category convertStringToCategory(String category) {
        Category category1 = new Category();
        category1.setTitle(category);
        return category1;
    }

    @Override
    public ResponseEntity<Product> addProduct(ProductDto productDto) throws ProductNotFoundException{
        Product product=convertProductDtoToProduct(productDto);
        Category category;
/*        if(productDto.getCategory().getId()!=null) {
            Optional<Category> categoryOptional = categoryRepository.findCategoryById(productDto.getCategory().getId());
            if(categoryOptional.isPresent()){
                category = categoryOptional.get();
            }else{
                throw new ProductNotFoundException("No valid Category with the given id exists", productDto.getCategory().getId());
            }
            product.setCategory(category);
        }*/

        product= productRepository.save(product);
        ResponseEntity<Product> response = new ResponseEntity<Product>(product, HttpStatus.CREATED);
        return response;
    }

    @Override
    public ResponseEntity<Product> updateProduct(UUID id, ProductDto productDto) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found",id));

        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setCategory(productDto.getCategory());
        Product product= productRepository.save(existingProduct);
        ResponseEntity<Product> response = new ResponseEntity<>(product, HttpStatus.OK);
        return response;
    }
}
