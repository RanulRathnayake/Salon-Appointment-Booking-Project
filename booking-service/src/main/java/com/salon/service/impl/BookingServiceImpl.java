package com.salon.service.impl;

import com.salon.domain.BookingStatus;
import com.salon.dto.BookingReqDTO;
import com.salon.dto.SalonDTO;
import com.salon.dto.ServiceDTO;
import com.salon.dto.UserDTO;
import com.salon.modal.Booking;
import com.salon.modal.SalonReport;
import com.salon.repository.BookingRepository;
import com.salon.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingReqDTO booking,
                                 UserDTO user,
                                 SalonDTO salon,
                                 Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime,bookingEndTime);

        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();

        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO,LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getAllBookingBySalon(salonDTO.getId());

        if(bookingStartTime.isBefore(salonDTO.getOpenTime()) || bookingEndTime.isAfter(salonDTO.getCloseTime())){
            throw new Exception("Booking time must be within salon's working hours");
        }
        for (Booking existingBooking:existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if(bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("Time slot is already booked");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getAllBookingByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getAllBookingBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public List<Booking> getAllBookingByDate(LocalDate date, Long salonId) {
        List<Booking> allBooking = getAllBookingBySalon(salonId);
        if(date == null){
            return allBooking;
        }

        return allBooking.stream().filter(booking -> isSameDate(booking.getStartTime(),date)).collect(Collectors.toList());
        // || isSameDate(booking.getEndTime(),date) *No need cuz start time and end time are in same day.

    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }


    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new Exception("Booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getAllBookingBySalon(salonId);

        Double totalEarnings = bookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setCancelledBooking(cancelledBookings.size());
        salonReport.setTotalRefund(totalRefund);
        salonReport.setTotalBooking(bookings.size());
        salonReport.setTotalEarnings(totalEarnings);
        return salonReport;
    }
}
