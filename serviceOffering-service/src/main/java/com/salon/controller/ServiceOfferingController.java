package com.salon.controller;

import com.salon.modal.ServiceOffering;
import com.salon.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<ServiceOffering>> getservicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ){
        List<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServicesBySalonId(salonId,categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable Long id
    ) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingService.getserviceById(id);
        return ResponseEntity.ok(serviceOffering);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<List<ServiceOffering>> getServiceByIds(
            @PathVariable List<Long> ids
    ){
        List<ServiceOffering> serviceOfferings = serviceOfferingService.getserviceByIds(ids);

        return ResponseEntity.ok(serviceOfferings);

    }
}
