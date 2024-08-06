package com.example.dack4.repositories;

import com.example.dack4.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    public Food findByName(String name);
}
