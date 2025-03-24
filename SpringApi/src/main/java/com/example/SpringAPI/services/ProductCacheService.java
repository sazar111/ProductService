package com.example.SpringAPI.services;

import com.example.SpringAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProductCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String USER_KEY_PREFIX = "Product:"; // Redis key prefix

    // Save user to Redis cache
    public void saveProduct(Product product) {
        String key = USER_KEY_PREFIX + product.getId();
        redisTemplate.opsForValue().set(key, product, 30, TimeUnit.MINUTES); // Cache expiry: 30 minutes
    }

    // Retrieve user from Redis cache
    public Optional<Product> getProductFromCache(UUID userId) {
        Object product=  redisTemplate.opsForValue().get(USER_KEY_PREFIX + userId);
        return Optional.ofNullable((Product) product);
    }

    // Delete user from Redis cache
    public void deleteUserFromCache(String userId) {
        redisTemplate.delete(USER_KEY_PREFIX + userId);
    }
}

