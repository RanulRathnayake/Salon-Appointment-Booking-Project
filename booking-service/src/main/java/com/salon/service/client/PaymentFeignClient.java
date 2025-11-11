package com.salon.service.client;


import com.salon.domain.PaymentMethod;
import com.salon.dto.BookingDTO;
import com.salon.dto.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentFeignClient {


    @PostMapping("/api/payments/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink (
            @RequestBody BookingDTO bookingDTO,
            @RequestParam PaymentMethod paymentMethod
    );
}
