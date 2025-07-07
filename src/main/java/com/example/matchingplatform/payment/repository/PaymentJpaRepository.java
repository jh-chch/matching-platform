package com.example.matchingplatform.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matchingplatform.payment.repository.entity.PaymentEntity;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
