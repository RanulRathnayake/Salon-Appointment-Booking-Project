package com.salon.payload.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private String paymentLinkUrl;
    private String getPaymentLinkId;
}
