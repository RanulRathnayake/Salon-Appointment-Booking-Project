package com.salon.service;

import com.salon.modal.ServiceOffering;
import com.salon.payload.dto.CategoryDTO;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.ServiceDTO;

import java.util.List;

public interface ServiceOfferingService {

    ServiceOffering createService(
            SalonDTO salonDTO,
            ServiceDTO serviceDTO,
            CategoryDTO categoryDTO
    );

    ServiceOffering updateService(
            Long id,
            ServiceOffering service
    ) throws Exception;

    List<ServiceOffering> getAllServicesBySalonId(
            Long salonId,
            Long categoryid);

    List<ServiceOffering> getServicesByIds(
            List<Long> ids
    );

    ServiceOffering getServiceById(
            Long id
    ) throws Exception;


    //Must add deleteService
}