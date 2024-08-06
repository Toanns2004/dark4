package com.example.dack4.controllers;

import com.example.dack4.config.MessageResponse;
import com.example.dack4.models.Category;
import com.example.dack4.models.Food;
import com.example.dack4.services.CategoryService;
import com.example.dack4.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Food>> getFoods(Pageable pageable) {
        Page<Food> foods = foodService.getFoods(pageable);
        return ResponseEntity.ok(foods);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("discount") String discount,
            @RequestParam("category_id") Long categoryId,
            @RequestParam("image") MultipartFile image) {

        try {
            // Xử lý file hình ảnh
            String imageName = null;
            if (image != null && !image.isEmpty()) {
                // Lưu file hình ảnh vào thư mục
                String originalFilename = image.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = new Date().getTime() + fileExtension;
                String imagePath = "public/img/" + uniqueFilename;

                File uploadDirectory = new File("public/img");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                Files.copy(image.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
                imageName = uniqueFilename;
            }

            // Tạo thực phẩm mới
            Category category = categoryService.findCategoryId(categoryId);
            Food newFood = new Food();
            newFood.setName(name);
            newFood.setDescription(description);
            newFood.setPrice(price);
            newFood.setDiscount(discount);
            newFood.setCategory(category);
            newFood.setImage(imageName);

            Food createdFood = foodService.addFood(newFood);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id,
                                        @RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") Double price,
                                        @RequestParam(value = "image") MultipartFile image,
                                        @RequestParam("discount") String discount,
                                        @RequestParam("category_id") Long categoryId) {
        try {
            Food updateFood = foodService.findFoodById(id);
            if (updateFood == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found");
            }

            if (image != null) {
                String oldImageName = updateFood.getImage();
                if (oldImageName != null) {
                    String imagePath = "public/img/" + oldImageName;
                    File oldImageFile = new File(imagePath);
                    if (oldImageFile.exists()) {
                        oldImageFile.delete();
                    }
                }

                File uploadDirectory = new File("public/img");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                String originalFilename = image.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = new Date().getTime() + fileExtension;

                String newImagePath = "public/img/" + uniqueFilename;
                Files.copy(
                        image.getInputStream(),
                        Paths.get(newImagePath),
                        StandardCopyOption.REPLACE_EXISTING
                );
                updateFood.setImage(uniqueFilename);
            }

            // Update food information
            Category category = categoryService.findCategoryId(categoryId);
            updateFood.setName(name);
            updateFood.setDescription(description);
            updateFood.setPrice(price);
            updateFood.setDiscount(discount);
            updateFood.setCategory(category);

            foodService.updateFood(updateFood);


            return ResponseEntity.ok(new MessageResponse("Food updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Failed to update food: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        Food deleteFood = foodService.findFoodById(id);
        if (deleteFood != null) {
            try {
                // Xóa hình ảnh
                String imageName = deleteFood.getImage();
                if (imageName != null) {
                    String imagePath = "public/img/" + imageName;
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        imageFile.delete();
                    }
                }

                foodService.deleteFoodById(id);

                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


    @GetMapping("/{id}")
    public Food getFood(@PathVariable Long id) {
        return foodService.findFoodById(id);
    }
}
