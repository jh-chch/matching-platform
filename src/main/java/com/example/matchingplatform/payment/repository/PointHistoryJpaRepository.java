package com.example.matchingplatform.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matchingplatform.payment.repository.entity.PointHistoryEntity;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistoryEntity, Long> {
    boolean existsByPaymentKey(String paymentKey);
}
