package com.example.SpringAPI.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.FakeStoreProductDto;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakeStoreProductServiceTest {

    @InjectMocks
    FakeStoreProductService fakeStoreProductService;

    @Mock
    RestTemplate restTemplate;

    private UUID sampleId;
    private FakeStoreProductDto fakeStoreProductDto;
    private ProductDto productDto;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(sampleId);
        fakeStoreProductDto.setTitle("Sample Title");
        fakeStoreProductDto.setPrice(10);
        fakeStoreProductDto.setImage("sample.jpg");
        fakeStoreProductDto.setDescription("Sample Description");
        fakeStoreProductDto.setCategory("Electronics");

        productDto = new ProductDto();
        sampleProduct = new Product();
    }

    @Test
    void getProductById_Success() throws ProductNotFoundException {
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto.class))).thenReturn(fakeStoreProductDto);

        Product result = fakeStoreProductService.getProductById(sampleId);
        assertNotNull(result);
        assertEquals(fakeStoreProductDto.getTitle(), result.getTitle());
    }

    @Test
    void getProductById_NotFound() {
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto.class))).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> fakeStoreProductService.getProductById(sampleId));
    }

    @Test
    void getAllProducts_Success() {
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto[].class))).thenReturn(new FakeStoreProductDto[]{fakeStoreProductDto});

        List<Product> products = fakeStoreProductService.getAllProducts(Map.of());
        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    void getAllCategories_Success() {
        when(restTemplate.getForObject(anyString(), eq(String[].class))).thenReturn(new String[]{"Electronics"});

        List<Category> categories = fakeStoreProductService.getAllCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    void getProductsByCategory_Success() {
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto[].class))).thenReturn(new FakeStoreProductDto[]{fakeStoreProductDto});

        List<Product> products = fakeStoreProductService.getProductsByCategory("Electronics");
        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    void addProduct_Success() {
        when(restTemplate.postForObject(anyString(), any(ProductDto.class), eq(FakeStoreProductDto.class))).thenReturn(fakeStoreProductDto);

        ResponseEntity<Product> response = fakeStoreProductService.addProduct(productDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateProduct_Success() throws ProductNotFoundException {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(FakeStoreProductDto.class)))
                .thenReturn(new ResponseEntity<>(fakeStoreProductDto, HttpStatus.OK));

        ResponseEntity<Product> response = fakeStoreProductService.updateProduct(sampleId, productDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateProduct_NotFound() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(FakeStoreProductDto.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThrows(ProductNotFoundException.class, () -> fakeStoreProductService.updateProduct(sampleId, productDto));
    }
}
