package com.salon.service;

import com.salon.modal.Salon;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.UserDTO;

import java.util.List;


public interface SalonService {

    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(SalonDTO req, UserDTO user, Long salonId);

    List<Salon> getAllSalon();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}
