package com.salon.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingReqDTO {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Long> serviceIds;
}
