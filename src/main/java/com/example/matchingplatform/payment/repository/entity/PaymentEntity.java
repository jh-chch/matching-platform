package com.example.matchingplatform.payment.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.util.Assert;

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

@Table(name = "payment")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDateTime requestedDateTime;

    private LocalDateTime approvedDateTime;

    private LocalDateTime canceledDateTime;

    private String cancelReason;

    @Builder
    public PaymentEntity(MemberEntity memberEntity, String paymentKey, BigDecimal amount, PaymentStatus status) {
        Assert.notNull(memberEntity, "memberEntity는 필수입니다.");
        Assert.hasText(paymentKey, "paymentKey는 필수입니다.");
        Assert.notNull(amount, "amount는 필수입니다.");
        Assert.isTrue(amount.signum() >= 0, "amount는 0 이상이어야 합니다.");
        Assert.notNull(status, "status는 필수입니다.");

        this.memberEntity = memberEntity;
        this.paymentKey = paymentKey;
        this.amount = amount;
        this.status = status;
        this.requestedDateTime = LocalDateTime.now();
    }

    public void approve() {
        if (this.status != PaymentStatus.REQUESTED) {
            throw new PaymentException("승인 실패: 결제 요청 상태가 아닙니다.");
        }
        this.status = PaymentStatus.APPROVED;
        this.approvedDateTime = LocalDateTime.now();
    }

    public void cancel(String reason) {
        this.status = PaymentStatus.CANCELED;
        this.canceledDateTime = LocalDateTime.now();
        this.cancelReason = reason;
    }
}
