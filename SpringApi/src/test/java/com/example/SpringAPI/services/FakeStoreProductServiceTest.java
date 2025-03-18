package com.example.SpringAPI.services;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.dtos.FakeStoreProductDto;
import com.example.SpringAPI.dtos.ProductDto;
import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FakeStoreProductServiceTest {

    private FakeStoreProductService productService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        productService = new FakeStoreProductService(restTemplate);
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        UUID productId = UUID.randomUUID();
        FakeStoreProductDto fakeProduct = new FakeStoreProductDto(
                productId, "Laptop", 1200, "Electronics", "High-performance laptop", "image-url.jpg"
        );

        when(restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class))
                .thenReturn(fakeProduct);

        Product product = productService.getProductById(productId);

        assertNotNull(product);
        assertEquals(fakeProduct.getId(), product.getId());
        assertEquals(fakeProduct.getTitle(), product.getTitle());
        assertEquals(fakeProduct.getPrice(), product.getPrice());
        assertEquals(fakeProduct.getDescription(), product.getDescription());
        assertEquals(fakeProduct.getImage(), product.getImage());
        assertEquals(fakeProduct.getCategory(), product.getCategory().getTitle());
    }

    @Test
    void testGetProductById_NotFound() {
        UUID productId = UUID.randomUUID();

        when(restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class))
                .thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void testGetAllProducts() {
        FakeStoreProductDto[] fakeProducts = {
                new FakeStoreProductDto(UUID.randomUUID(), "Laptop", 1200, "Electronics", "High-performance laptop", "image1.jpg"),
                new FakeStoreProductDto(UUID.randomUUID(), "Phone", 800, "Electronics", "Latest smartphone", "image2.jpg")
        };

        when(restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class))
                .thenReturn(fakeProducts);

        List<Product> products = new ArrayList<>(productService.getAllProducts(null, 0, 10, "id").getContent());

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(fakeProducts[0].getTitle(), products.get(0).getTitle());
        assertEquals(fakeProducts[1].getTitle(), products.get(1).getTitle());
    }

    @Test
    void testGetAllCategories() {
        String[] categories = {"Electronics", "Clothing", "Books"};

        when(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class))
                .thenReturn(categories);

        List<Category> categoryList = productService.getAllCategories();

        assertNotNull(categoryList);
        assertEquals(3, categoryList.size());
        assertEquals("Electronics", categoryList.get(0).getTitle());
        assertEquals("Clothing", categoryList.get(1).getTitle());
        assertEquals("Books", categoryList.get(2).getTitle());
    }

    @Test
    void testGetProductsByCategory() {
        String categoryName = "Electronics";
        FakeStoreProductDto[] fakeProducts = {
                new FakeStoreProductDto(UUID.randomUUID(), "Laptop", 1200, categoryName, "High-performance laptop", "image1.jpg"),
                new FakeStoreProductDto(UUID.randomUUID(), "Phone", 800, categoryName, "Latest smartphone", "image2.jpg")
        };

        when(restTemplate.getForObject("https://fakestoreapi.com/products/category/" + categoryName, FakeStoreProductDto[].class))
                .thenReturn(fakeProducts);

        List<Product> products = productService.getProductsByCategory(categoryName);

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(categoryName, products.get(0).getCategory().getTitle());
        assertEquals(categoryName, products.get(1).getCategory().getTitle());
    }

    @Test
    void testAddProduct() {
        ProductDto productDto = new ProductDto("Tablet", 500, "Electronics", "Android tablet", "image-tablet.jpg");

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto(
                UUID.randomUUID(), productDto.getTitle(), productDto.getPrice(), productDto.getCategory().toString(), productDto.getDescription(), productDto.getImage()
        );

        when(restTemplate.postForObject(eq("https://fakestoreapi.com/products/"), any(ProductDto.class), eq(FakeStoreProductDto.class)))
                .thenReturn(fakeStoreProductDto);

        ResponseEntity<Product> response = productService.addProduct(productDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDto.getTitle(), response.getBody().getTitle());
    }

    @Test
    void testUpdateProduct_Success() throws ProductNotFoundException {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto("Updated Laptop", 1500, "Electronics", "Updated laptop specs", "image-laptop-updated.jpg");

        FakeStoreProductDto updatedFakeProduct = new FakeStoreProductDto(
                productId, productDto.getTitle(), productDto.getPrice(), productDto.getCategory().toString(), productDto.getDescription(), productDto.getImage()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(productDto, headers);

        ResponseEntity<FakeStoreProductDto> fakeResponse = new ResponseEntity<>(updatedFakeProduct, HttpStatus.OK);

        when(restTemplate.exchange(eq("https://fakestoreapi.com/products/" + productId), eq(HttpMethod.PUT), any(HttpEntity.class), eq(FakeStoreProductDto.class)))
                .thenReturn(fakeResponse);

        ResponseEntity<Product> response = productService.updateProduct(productId, productDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDto.getTitle(), response.getBody().getTitle());
    }
}
