package com.salon.service.client;


import com.salon.payload.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("SERVICEOFFERING-SERVICE")
public interface ServiceOfferingFeignClient {

    @GetMapping("/api/service-offering/list/{ids}")
    public ResponseEntity<List<ServiceDTO>> getServiceByIds(
            @PathVariable List<Long> ids
    );
}
