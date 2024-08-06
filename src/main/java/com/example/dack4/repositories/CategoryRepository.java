package com.example.dack4.repositories;

import com.example.dack4.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String name);
}
