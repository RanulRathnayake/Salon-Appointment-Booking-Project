package com.salon.controller;

import com.salon.CategoryServiceApplication;
import com.salon.modal.Category;
import com.salon.payload.dto.SalonDTO;
import com.salon.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category
            ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L); //temp

        Category newcategory = categoryService.saveCategory(category, salonDTO);
        return ResponseEntity.ok(newcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id
    ) throws Exception{
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L); //temp

        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}
