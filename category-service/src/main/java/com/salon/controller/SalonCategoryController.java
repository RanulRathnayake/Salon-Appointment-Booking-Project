package com.salon.controller;

import com.salon.CategoryServiceApplication;
import com.salon.modal.Category;
import com.salon.payload.dto.SalonDTO;
import com.salon.service.CategoryService;
import com.salon.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        Category newcategory = categoryService.saveCategory(category, salonDTO);
        return ResponseEntity.ok(newcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("Category Deleted Successfully");
    }

    @GetMapping("salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCaregoryByidAndSalonId(
            @PathVariable Long id,
            @PathVariable Long salonId
    )throws Exception {
        Category category = categoryService.getCategoryByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(category);
    }
}
