package com.salon.service.impl;

import com.salon.modal.Category;
import com.salon.payload.dto.SalonDTO;
import com.salon.repository.CategoryRepository;
import com.salon.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());
        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category==null){
            throw new Exception("Category not exist with id" + id);
        }else
            return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception{
        Category category = getCategoryById(id);
        if (category.getSalonId()!=salonId){
            throw new Exception("You don't have permission to delete this category");
        }else
            categoryRepository.deleteById(id);
    }
}
