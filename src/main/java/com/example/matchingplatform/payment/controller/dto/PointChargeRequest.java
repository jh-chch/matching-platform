package com.example.matchingplatform.payment.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PointChargeRequest {
    private String pgType;
    private String paymentKey;
    private String email;

    @Builder
    public PointChargeRequest(String pgType, String paymentKey, String email) {
        this.pgType = pgType;
        this.paymentKey = paymentKey;
        this.email = email;
    }
}
