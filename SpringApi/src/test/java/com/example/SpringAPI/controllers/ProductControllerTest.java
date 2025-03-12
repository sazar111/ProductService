package com.example.SpringAPI.controllers;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.Exceptions.UnauthorizedException;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import com.example.SpringAPI.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private Product sampleProduct;
    private UUID sampleId;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        sampleProduct = new Product();
        sampleProduct.setId(sampleId);
        sampleProduct.setTitle("Sample Title");
        sampleProduct.setDescription("Sample Description");
    }

    /*@Test
    void getProductByIdTest() throws ProductNotFoundException, UnauthorizedException {
        when(productService.getProductById(sampleId)).thenReturn(sampleProduct);

        Product resultProduct = productController.getProductById(sampleId,"");
        assertNotNull(resultProduct);
        assertEquals(sampleProduct, resultProduct);
    }*/

/*    @Test
    void getProductById_NotFound() throws ProductNotFoundException {
        UUID invalidId = UUID.randomUUID();
        when(productService.getProductById(invalidId)).thenThrow(new ProductNotFoundException("Product not found",invalidId));

        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(invalidId,""));
    }*/

    @Test
    void getAllProducts() {
        List<Product> productList = Arrays.asList(sampleProduct);
        when(productService.getAllProducts(any(Map.class))).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProducts(Map.of());
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category());
        when(productService.getAllCategories()).thenReturn(categories);

        List<Category> result = productController.getAllCategories();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getProductsByCategory() {
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getProductsByCategory("Electronics")).thenReturn(products);

        List<Product> result = productController.getProductsByCategory("Electronics");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getProductsByCategory_NotFound() {
        when(productService.getProductsByCategory("InvalidCategory")).thenReturn(List.of());

        List<Product> result = productController.getProductsByCategory("InvalidCategory");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addProduct() throws ProductNotFoundException {
        ProductDto productDto = new ProductDto();
        when(productService.addProduct(any(ProductDto.class))).thenReturn(ResponseEntity.ok(sampleProduct));

        ResponseEntity<Product> response = productController.addProduct(productDto);
        assertNotNull(response);
        assertEquals(sampleProduct, response.getBody());
    }

    @Test
    void addProduct_Failure() throws ProductNotFoundException {
        ProductDto productDto = new ProductDto();
        when(productService.addProduct(any(ProductDto.class))).thenThrow(new RuntimeException("Failed to add product"));

        assertThrows(RuntimeException.class, () -> productController.addProduct(productDto));
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        ProductDto productDto = new ProductDto();
        when(productService.updateProduct(eq(sampleId), any(ProductDto.class))).thenReturn(ResponseEntity.ok(sampleProduct));

        ResponseEntity<Product> response = productController.updateProduct(productDto, sampleId);
        assertNotNull(response);
        assertEquals(sampleProduct, response.getBody());
    }

    @Test
    void updateProduct_NotFound() throws ProductNotFoundException {
        ProductDto productDto = new ProductDto();
        UUID invalidId = UUID.randomUUID();
        when(productService.updateProduct(eq(invalidId), any(ProductDto.class))).thenThrow(new ProductNotFoundException("Product not found",invalidId));

        assertThrows(ProductNotFoundException.class, () -> productController.updateProduct(productDto, invalidId));
    }
}
