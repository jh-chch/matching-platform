package com.example.matchingplatform.payment.repository.entity;

import java.math.BigDecimal;

import com.example.matchingplatform.common.BaseDateTimeEntity;
import com.example.matchingplatform.member.repository.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "point_history")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryEntity extends BaseDateTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointHistoryType type;

    @Column(nullable = false, unique = true)
    private String paymentKey;

    private String reason;

    @Builder
    public PointHistoryEntity(MemberEntity memberEntity, BigDecimal amount, PointHistoryType type, String paymentKey, String reason) {
        this.memberEntity = memberEntity;
        this.amount = amount;
        this.type = type;
        this.paymentKey = paymentKey;
        this.reason = reason;
    }
}
