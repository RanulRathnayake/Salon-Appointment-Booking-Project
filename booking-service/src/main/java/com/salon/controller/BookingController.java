package com.salon.controller;

import com.salon.domain.BookingStatus;
import com.salon.dto.*;
import com.salon.mapper.BookingMapper;
import com.salon.modal.Booking;
import com.salon.modal.SalonReport;
import com.salon.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping()
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingReqDTO bookinDTO
    ) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonId);
        salonDTO.setOpenTime(LocalDateTime.now());
        salonDTO.setCloseTime(LocalDateTime.now().plusHours(12));

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(500);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();
        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookinDTO, userDTO, salonDTO, serviceDTOSet);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        List<Booking> bookings = bookingService.getAllBookingByCustomer(userDTO.getId());

        return ResponseEntity.ok(getBookingDTO(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        List<Booking> bookings = bookingService.getAllBookingBySalon(salonDTO.getId());
        return ResponseEntity.ok(getBookingDTO(bookings));
    }
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
            ) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getAllBookingByDate(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date //then it is not mandatory
            ){
        List<Booking> bookings = bookingService.getAllBookingByDate(date, salonId);

        return ResponseEntity.ok(getBookingSlotDTO(bookings));
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(){
        SalonReport salonReport =bookingService.getSalonReport(1L);
        return ResponseEntity.ok(salonReport);
    }

    private  Set<BookingDTO> getBookingDTO(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }
    private List<BookingSlotDTO> getBookingSlotDTO(List<Booking> bookings){
        return  bookings.stream()
                .map(booking -> {
                    BookingSlotDTO bookingSlotDTO = new BookingSlotDTO();
                    bookingSlotDTO.setStartTime(booking.getStartTime());
                    bookingSlotDTO.setEndtTime(booking.getEndTime());
                    return bookingSlotDTO;
                }).collect(Collectors.toList());
    }
}
