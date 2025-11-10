package com.salon.controller;

import com.salon.modal.ServiceOffering;
import com.salon.payload.dto.CategoryDTO;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.ServiceDTO;
import com.salon.service.ServiceOfferingService;
import com.salon.service.client.CategoryFeignClient;
import com.salon.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO= salonFeignClient.getSalonByOwnerId(jwt).getBody();
        if (salonDTO== null){
            throw new Exception("Salon not found");
        }

        CategoryDTO categoryDTO = categoryFeignClient.getCaregoryByidAndSalonId(serviceDTO.getCategoryId(), salonDTO.getId()).getBody();
        if (categoryDTO== null){
            throw new Exception("Category not found");
        }

        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOffering);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOffering1);
    }


}
