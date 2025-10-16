package com.salon.service;

import com.salon.domain.PaymentMethod;
import com.salon.modal.PaymentOrder;
import com.salon.payload.dto.BookingDTO;
import com.salon.payload.dto.UserDTO;
import com.salon.payload.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(
            UserDTO user,
            BookingDTO bookingDTO,
            PaymentMethod paymentMethod
    ) throws StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    String createStripePaymentLink(UserDTO userDTO,
                                   Long amount,
                                   Long orderId) throws StripeException;

    Boolean ProceedPaymentOrder (PaymentOrder paymentOrder,
                                 String paymentId, String paymentLinkId);
}
