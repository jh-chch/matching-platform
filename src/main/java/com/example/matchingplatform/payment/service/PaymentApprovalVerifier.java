package com.example.matchingplatform.payment.service;

public interface PaymentApprovalVerifier {
    PaymentApprovalResult verifyApproved(String paymentKey);
}
