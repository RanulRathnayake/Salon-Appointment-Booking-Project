package com.salon.modal;

import lombok.Data;

@Data
public class SalonReport {
    private Long salonId;
    private String salonName;
    private Double totalEarnings;
    private Integer totalBooking;
    private Integer cancelledBooking;
    private Double totalRefund;
}
