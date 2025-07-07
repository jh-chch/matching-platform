package com.example.matchingplatform.payment.repository.entity;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}