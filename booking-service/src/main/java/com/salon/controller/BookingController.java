package com.salon.controller;

import com.salon.domain.BookingStatus;
import com.salon.domain.PaymentMethod;
import com.salon.dto.*;
import com.salon.mapper.BookingMapper;
import com.salon.modal.Booking;
import com.salon.modal.SalonReport;
import com.salon.service.BookingService;
import com.salon.service.client.PaymentFeignClient;
import com.salon.service.client.SalonFeignClient;
import com.salon.service.client.ServiceOfferingFeignClient;
import com.salon.service.client.UserFeignClient;
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
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping()
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingReqDTO bookingReqDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salonDTO = salonFeignClient.getSalonById(salonId).getBody();

        List<ServiceDTO> serviceDTOList = serviceOfferingFeignClient.getServiceByIds(bookingReqDTO.getServiceIds()).getBody();

        Booking booking = bookingService.createBooking(bookingReqDTO, userDTO, salonDTO, serviceDTOList);

        BookingDTO bookingDTO = BookingMapper.toDTO(booking);

        paymentFeignClient.createPaymentLink(bookingDTO, paymentMethod); // have to pass jwt or not? Check

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        List<Booking> bookings = bookingService.getAllBookingByCustomer(userDTO.getId());

        return ResponseEntity.ok(getBookingDTO(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

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
    public ResponseEntity<SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        SalonReport salonReport =bookingService.getSalonReport(salonDTO.getId());
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
