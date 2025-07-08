package com.example.matchingplatform.payment.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PointChargeRequest {
    @NotBlank
    private String pgType;
    
    @NotBlank
    private String paymentKey;
    
    @Email
    @NotBlank
    private String email;

    @Builder
    public PointChargeRequest(String pgType, String paymentKey, String email) {
        this.pgType = pgType;
        this.paymentKey = paymentKey;
        this.email = email;
    }
}
