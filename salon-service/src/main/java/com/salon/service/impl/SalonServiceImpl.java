package com.salon.service.impl;

import com.salon.modal.Salon;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.UserDTO;
import com.salon.repository.SalonRepository;
import com.salon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());

        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO req, UserDTO user, Long salonId) {
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon != null && existingSalon.getOwnerId().equals(user.getId())){
            existingSalon.setName(req.getName() != null ? req.getName() : existingSalon.getName());
            existingSalon.setAddress(req.getAddress()!=null ? req.getAddress() : existingSalon.getAddress());
            existingSalon.setEmail(req.getEmail() !=null ? req.getEmail() : existingSalon.getEmail());
            existingSalon.setCity(req.getCity() != null ? req.getCity() : existingSalon.getCity());
            existingSalon.setImages(req.getImages() != null ? req.getImages() : existingSalon.getImages());
            existingSalon.setOpenTime(req.getOpenTime() != null ? req.getOpenTime() : existingSalon.getOpenTime());
            existingSalon.setCloseTime(req.getCloseTime() != null ? req.getCloseTime() : existingSalon.getCloseTime());
            existingSalon.setPhoneNumber(req.getPhoneNumber() != null ? req.getPhoneNumber() : existingSalon.getPhoneNumber());
            //existingSalon.setOwnerId(user.getId());

            return salonRepository.save(existingSalon);

        }
        return null;
    }

    @Override
    public List<Salon> getAllSalon() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon==null){
            throw new Exception("salon not exist");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
