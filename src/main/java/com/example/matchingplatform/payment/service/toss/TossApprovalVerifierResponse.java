package com.example.matchingplatform.payment.service.toss;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TossApprovalVerifierResponse {
    private String paymentKey;
    private String status;
    private ZonedDateTime approvedAt;
    private BigDecimal totalAmount;
}
