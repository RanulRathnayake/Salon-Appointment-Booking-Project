package com.salon.repository;

import com.salon.modal.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerID);
    List<Booking> findBySalonId(Long salonID);
}
