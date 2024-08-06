package com.example.dack4.services;

import com.example.dack4.models.Food;
import com.example.dack4.repositories.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Page<Food> getFoods(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public Food findFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }


    public void deleteFoodById(Long id) {
        foodRepository.deleteById(id);
    }

    public void updateFood(Food food) {
        foodRepository.save(food);
    }
}
