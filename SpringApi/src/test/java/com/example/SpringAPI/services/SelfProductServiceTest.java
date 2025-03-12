package com.example.SpringAPI.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import com.example.SpringAPI.repositories.CategoryRepository;
import com.example.SpringAPI.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelfProductServiceTest {

    @InjectMocks
    SelfProductService selfProductService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    private UUID sampleId;
    private Product sampleProduct;
    private ProductDto productDto;
    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        sampleProduct = new Product();
        sampleProduct.setId(sampleId);
        sampleProduct.setTitle("Sample Product");
        sampleProduct.setDescription("Sample Description");

        sampleCategory = new Category();
        sampleCategory.setTitle("Electronics");
        sampleProduct.setCategory(sampleCategory);

        productDto = new ProductDto();
        productDto.setTitle("Updated Product");
        productDto.setDescription("Updated Description");
    }

    @Test
    void getProductById_Success() throws ProductNotFoundException {
        when(productRepository.findById(sampleId)).thenReturn(Optional.of(sampleProduct));
        Product result = selfProductService.getProductById(sampleId);
        assertNotNull(result);
        assertEquals(sampleProduct.getTitle(), result.getTitle());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(sampleId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> selfProductService.getProductById(sampleId));
    }

    @Test
    void getAllProducts_Success() {
        when(productRepository.findAll((Specification<Product>) any())).thenReturn(Collections.singletonList(sampleProduct));
        List<Product> products = selfProductService.getAllProducts(Map.of());
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void getAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(sampleCategory));
        List<Category> categories = selfProductService.getAllCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    void getProductsByCategory_Success() {
        when(productRepository.getProductsByCategory(any(Category.class))).thenReturn(Collections.singletonList(sampleProduct));
        List<Product> products = selfProductService.getProductsByCategory("Electronics");
        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    void addProduct_Success() throws ProductNotFoundException {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);
        ResponseEntity<Product> response = selfProductService.addProduct(productDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateProduct_Success() throws ProductNotFoundException {
        when(productRepository.findById(sampleId)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);
        ResponseEntity<Product> response = selfProductService.updateProduct(sampleId, productDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateProduct_NotFound() {
        when(productRepository.findById(sampleId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> selfProductService.updateProduct(sampleId, productDto));
    }
}
