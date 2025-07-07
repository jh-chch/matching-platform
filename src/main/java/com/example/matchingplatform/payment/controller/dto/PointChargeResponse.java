package com.example.matchingplatform.payment.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PointChargeResponse {

    private final String email;
    private final String paymentKey;
    private final BigDecimal chargedAmount;
    private final BigDecimal currentPoint;
    private final LocalDateTime chargedDateTime;

    @Builder
    public PointChargeResponse(String email, String paymentKey, BigDecimal chargedAmount,
            BigDecimal currentPoint, LocalDateTime chargedDateTime) {
        this.email = email;
        this.paymentKey = paymentKey;
        this.chargedAmount = chargedAmount;
        this.currentPoint = currentPoint;
        this.chargedDateTime = chargedDateTime;
    }
}
