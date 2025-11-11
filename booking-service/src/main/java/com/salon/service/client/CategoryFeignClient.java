package com.salon.service.client;


import com.salon.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryFeignClient {

//    @GetMapping("/api/categories/{id}")
//    public ResponseEntity<CategoryDTO> getCaregoryByid(@PathVariable Long id)throws Exception ;

    @GetMapping("/api/categories/salon-owner/salon/{salonId}/category/{id}")
    public ResponseEntity<CategoryDTO> getCaregoryByidAndSalonId(
            @PathVariable Long id,
            @PathVariable Long salonId
    )throws Exception;
}
