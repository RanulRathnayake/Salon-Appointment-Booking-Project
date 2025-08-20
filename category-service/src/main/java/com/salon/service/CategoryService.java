package com.salon.service;

import com.salon.modal.Category;
import com.salon.payload.dto.SalonDTO;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    List<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long id, Long salonId) throws Exception;
}
