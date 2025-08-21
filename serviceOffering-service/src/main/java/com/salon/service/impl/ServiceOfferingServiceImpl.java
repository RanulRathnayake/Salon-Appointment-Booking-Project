package com.salon.service.impl;

import com.salon.modal.ServiceOffering;
import com.salon.payload.dto.CategoryDTO;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.ServiceDTO;
import com.salon.repository.ServiceOfferingRepository;
import com.salon.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private ServiceOfferingRepository serviceOfferingRepository;
    @Override
    public ServiceOffering createService(
            SalonDTO salonDTO,
            ServiceDTO serviceDTO,
            CategoryDTO categoryDTO) {

        ServiceOffering serviceOffering  = new ServiceOffering();

        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setDuration(serviceDTO.getDuration());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setCategoryId(categoryDTO.getId());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(
            Long serviceId,
            ServiceOffering service) throws Exception {

        ServiceOffering serviceOffering= serviceOfferingRepository.findById(serviceId).orElse(null);

        if(serviceOffering==null){
            throw new Exception("Service not exist with id"+serviceId);
        }else
            serviceOffering.setName(service.getName());
            serviceOffering.setImage(service.getImage());
            serviceOffering.setDescription(service.getDescription());
            serviceOffering.setDuration(service.getDuration());
            serviceOffering.setPrice(service.getPrice());

            return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public List<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryid) {
        List<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if (categoryid!=null){
            services = services.stream().filter((service)->service.getCategoryId()!=null &&
                    service.getCategoryId()==categoryid).toList(); //I droped collect
        }
        return services;
    }

    @Override
    public List<ServiceOffering> getServicesByIds(List<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return services;
    }

    @Override
    public ServiceOffering getserviceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);
        if (serviceOffering==null)
            throw new Exception("Service not exist with id" +id);

        return serviceOffering;
    }

    @Override
    public List<ServiceOffering> getserviceByIds(List<Long> ids) {
        return List.of();
    }
}
