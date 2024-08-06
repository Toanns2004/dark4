package com.example.dack4.controllers;

import com.example.dack4.models.Category;
import com.example.dack4.models.Food;
import com.example.dack4.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> getCategories(Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category categoryUpdate = categoryService.findCategoryId(id);
        if (categoryUpdate != null) {
            categoryUpdate.setName(category.getName());
            categoryUpdate.setDescription(category.getDescription());
            categoryService.updateCategory(categoryUpdate);
            return ResponseEntity.ok(categoryUpdate);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findCategoryId(id);
        if (category != null) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
