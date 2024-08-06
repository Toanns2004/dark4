package com.example.dack4.services;

import com.example.dack4.models.Category;
import com.example.dack4.models.Food;
import com.example.dack4.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findCategoryId(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}
