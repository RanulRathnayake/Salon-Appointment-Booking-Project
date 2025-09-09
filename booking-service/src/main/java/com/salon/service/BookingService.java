package com.salon.service;

import com.salon.domain.BookingStatus;
import com.salon.dto.BookingReqDTO;
import com.salon.dto.SalonDTO;
import com.salon.dto.ServiceDTO;
import com.salon.dto.UserDTO;
import com.salon.modal.Booking;
import com.salon.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingReqDTO booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getAllBookingByCustomer(Long customerId);

    List<Booking> getAllBookingBySalon(Long salonId);

    List<Booking> getAllBookingByDate(LocalDate date, Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    SalonReport getSalonReport(Long salonId);

}
