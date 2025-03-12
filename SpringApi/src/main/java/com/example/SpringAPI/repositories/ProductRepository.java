package com.example.SpringAPI.repositories;

import com.example.SpringAPI.models.Category;
import com.example.SpringAPI.models.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(Specification<Product> spec);
    Optional<Product> findById(UUID id);
    Product save(Product product);
    void deleteById(UUID id);
    List<Product> getProductsByCategory(Category category);

}
