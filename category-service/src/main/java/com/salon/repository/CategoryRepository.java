package com.salon.repository;


import com.salon.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findBySalonId(Long salonId);
    Category findByIdAndSalonId(Long id, Long salonId);
}
