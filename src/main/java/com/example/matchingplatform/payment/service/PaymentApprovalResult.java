package com.example.matchingplatform.payment.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentApprovalResult {
    private boolean success;
    private BigDecimal approvedAmount;
    private ZonedDateTime approvedDateTime;
    private String message;

    public static PaymentApprovalResult success(BigDecimal amount, ZonedDateTime time) {
        return new PaymentApprovalResult(true, amount, time, null);
    }

    public static PaymentApprovalResult failure(String reason) {
        return new PaymentApprovalResult(false, null, null, reason);
    }
}
