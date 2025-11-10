package com.salon.controller;

import com.salon.domain.PaymentMethod;
import com.salon.modal.PaymentOrder;
import com.salon.payload.dto.BookingDTO;

import com.salon.payload.PaymentLinkResponse;
import com.salon.payload.dto.UserDTO;
import com.salon.service.PaymentService;
import com.salon.service.client.UserFeignClient;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink (
            @RequestBody BookingDTO bookingDTO,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        PaymentLinkResponse paymentLinkResponse = paymentService.createOrder(userDTO, bookingDTO, paymentMethod);
        return ResponseEntity.ok(paymentLinkResponse);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId) {
        try {
            PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);
            return ResponseEntity.ok(paymentOrder);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);
        Boolean success = paymentService.ProceedPaymentOrder(paymentOrder,
                paymentId,
                paymentLinkId);
        return ResponseEntity.ok(success);
    }
}
