package com.example.SpringAPI.controllers;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.Exceptions.UnauthorizedException;
import com.example.SpringAPI.commons.AuthCommon;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Mock
    AuthCommon authCommon;

    private Product sampleProduct;
    private UUID sampleId;
    private ProductDto productDto;
    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();

        sampleProduct = new Product();
        sampleProduct.setId(sampleId);
        sampleProduct.setTitle("Sample Title");
        sampleProduct.setDescription("Sample Description");

        productDto = new ProductDto();
        productDto.setTitle("Updated Title");
        productDto.setDescription("Updated Description");

        sampleCategory = new Category();
        sampleCategory.setTitle("Electronics");
    }

    /**
     * Tests that retrieving a product by valid ID returns the product successfully.
     */
    @Test
    void getProductById_ValidId_ReturnsProduct() throws ProductNotFoundException, UnauthorizedException {
        when(productService.getProductById(sampleId)).thenReturn(sampleProduct);

        Product response = productController.getProductById(sampleId);

        assertNotNull(response);
        assertEquals(sampleProduct, response);
    }

    /**
     * Tests that retrieving a product by invalid ID throws ProductNotFoundException.
     */
    @Test
    void getProductById_InvalidId_ThrowsException() throws ProductNotFoundException {
        UUID invalidId = UUID.randomUUID();
        when(productService.getProductById(invalidId)).thenThrow(new ProductNotFoundException("Product not found", invalidId));

        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(invalidId));
    }

    /**
     * Tests that retrieving all products returns paginated results.
     */
    @Test
    void getProducts_WithPagination_ReturnsPagedProducts() {
        Page<Product> mockPage = new PageImpl<>(List.of(sampleProduct));
        when(productService.getAllProducts(any(), anyInt(), anyInt(), anyString())).thenReturn(mockPage);

        ResponseEntity<Page<Product>> response = productController.getProducts(Map.of(), 0, 10, "id");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    /**
     * Tests that retrieving all categories returns a list of categories.
     */
    @Test
    void getAllCategories_ReturnsCategoryList() {
        when(productService.getAllCategories()).thenReturn(List.of(sampleCategory));

        List<Category> response = productController.getAllCategories();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("Electronics", response.get(0).getTitle());
    }

    /**
     * Tests that retrieving products by category returns filtered products.
     */
    @Test
    void getProductsByCategory_ValidCategory_ReturnsProductList() {
        when(productService.getProductsByCategory("Electronics")).thenReturn(List.of(sampleProduct));

        List<Product> response = productController.getProductsByCategory("Electronics");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("Sample Title", response.get(0).getTitle());
    }

    /**
     * Tests that adding a new product returns the created product.
     */
    @Test
    void addProduct_ValidData_ReturnsCreatedProduct() throws ProductNotFoundException {
        when(productService.addProduct(any(ProductDto.class))).thenReturn(new ResponseEntity<>(sampleProduct, HttpStatus.CREATED));

        ResponseEntity<Product> response = productController.addProduct(productDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleProduct, response.getBody());
    }

    /**
     * Tests that updating a product with valid data returns the updated product.
     */
    @Test
    void updateProduct_WithValidData_ReturnsUpdatedProduct() throws ProductNotFoundException {
        when(productService.updateProduct(eq(sampleId), any(ProductDto.class)))
                .thenReturn(new ResponseEntity<>(sampleProduct, HttpStatus.OK));

        ResponseEntity<Product> response = productController.updateProduct(productDto, sampleId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleProduct, response.getBody());
    }
}
