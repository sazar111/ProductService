package com.example.SpringAPI.repositories;

import com.example.SpringAPI.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    Category save(Category entity);
    Optional<Category> findCategoryById(UUID id);
    List<Category> findAll();
}
