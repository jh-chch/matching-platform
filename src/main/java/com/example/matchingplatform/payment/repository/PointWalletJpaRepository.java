package com.example.matchingplatform.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matchingplatform.payment.repository.entity.PointWalletEntity;

public interface PointWalletJpaRepository extends JpaRepository<PointWalletEntity, Long> {
    Optional<PointWalletEntity> findByMemberEntityEmail(String email);
}
