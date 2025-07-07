package com.example.matchingplatform.payment.repository.entity;

import java.math.BigDecimal;

import org.springframework.util.Assert;

import com.example.matchingplatform.common.BaseDateTimeEntity;
import com.example.matchingplatform.member.repository.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "point_wallet")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointWalletEntity extends BaseDateTimeEntity {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private BigDecimal point;

    @Builder
    public PointWalletEntity(MemberEntity memberEntity, BigDecimal point) {
        Assert.notNull(memberEntity, "memberEntity는 필수입니다.");
        Assert.notNull(point, "point는 필수입니다.");
        Assert.isTrue(point.signum() >= 0, "포인트는 음수일 수 없습니다.");
        this.memberEntity = memberEntity;
        this.point = point;
    }

    /**
     * 포인트 충전
     */
    public void charge(BigDecimal amount) {
        Assert.notNull(amount, "충전 금액은 필수입니다.");
        Assert.isTrue(amount.signum() >= 0, "충전 금액은 음수일 수 없습니다.");
        this.point = this.point.add(amount);
    }

    /**
     * 포인트 사용
     */
    public void use(BigDecimal amount) {
        Assert.notNull(amount, "사용 금액은 필수입니다.");
        if (this.point.compareTo(amount) < 0) {
            throw new PointWalletException("포인트가 부족합니다.");
        }
        this.point = this.point.subtract(amount);
    }
}
