package com.salon.dto;

import com.salon.domain.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {

    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<Long> serviceIds;

    private BookingStatus status = BookingStatus.PENDING;

    private int totalPrice;
}
// salonId customerId serviceIds these fields better to be sent as DTO object