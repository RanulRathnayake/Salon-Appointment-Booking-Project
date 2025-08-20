package com.salon.controller;

import com.salon.modal.Category;
import com.salon.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/salon/{id}")
    public ResponseEntity<List<Category>> getCaregoriesBySalon(
            @PathVariable Long id
    ){
        List<Category> categories = categoryService.getAllCategoriesBySalon(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCaregoryByid(
            @PathVariable Long id
    )throws Exception {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
