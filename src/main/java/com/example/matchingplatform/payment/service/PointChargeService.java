package com.example.matchingplatform.payment.service;

import static com.example.matchingplatform.payment.service.PointServiceErrorCode.MEMBER_NOT_FOUND;
import static com.example.matchingplatform.payment.service.PointServiceErrorCode.PAYMENT_KEY_INVALID;
import static com.example.matchingplatform.payment.service.PointServiceErrorCode.PG_CLIENT_NOT_FOUND;
import static com.example.matchingplatform.payment.service.PointServiceErrorCode.POINT_ALREADY_CHARGED;
import static com.example.matchingplatform.payment.service.PointServiceErrorCode.POINT_CHARGE_CONCURRENCY_FAIL;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.matchingplatform.common.LogExecution;
import com.example.matchingplatform.member.repository.MemberJpaRepository;
import com.example.matchingplatform.member.repository.entity.MemberEntity;
import com.example.matchingplatform.payment.controller.dto.PointChargeRequest;
import com.example.matchingplatform.payment.controller.dto.PointChargeResponse;
import com.example.matchingplatform.payment.repository.PointHistoryJpaRepository;
import com.example.matchingplatform.payment.repository.PointWalletJpaRepository;
import com.example.matchingplatform.payment.repository.entity.PointHistoryEntity;
import com.example.matchingplatform.payment.repository.entity.PointHistoryType;
import com.example.matchingplatform.payment.repository.entity.PointWalletEntity;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointChargeService {

    private final MemberJpaRepository memberJpaRepository;
    private final PointWalletJpaRepository pointWalletJpaRepository;
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    private final Map<String, PaymentApprovalVerifier> paymentApprovalVerifier;
    private static final int MAX_RETRY = 5;

    @LogExecution("포인트 충전")
    public PointChargeResponse charge(PointChargeRequest request) {
        int retryCount = 0;

        while (true) {
            try {
                return doCharge(request);
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount >= MAX_RETRY) {
                    throw new PointServiceException(POINT_CHARGE_CONCURRENCY_FAIL);
                }
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Transactional
    protected PointChargeResponse doCharge(PointChargeRequest request) {
        // 회원 조회
        MemberEntity memberEntity = getMemberEntity(request);

        // 중복 충전 검사
        validateDuplicateCharge(request);

        // PG Client 가져오기
        PaymentApprovalVerifier verifier = getPaymentApprovalVerifier(request);

        // 승인건 검증
        PaymentApprovalResult paymentApprovalResult = verifyPayment(request, verifier);

        // 포인트 충전
        PointWalletEntity pointWalletEntity = updatePointWallet(request, memberEntity, paymentApprovalResult);

        // 히스토리 기록
        PointHistoryEntity historyEntity = savePointHistory(request, memberEntity, paymentApprovalResult);

        return PointChargeResponse.builder()
                .email(request.getEmail())
                .paymentKey(request.getPaymentKey())
                .chargedAmount(paymentApprovalResult.getApprovedAmount())
                .currentPoint(pointWalletEntity.getPoint())
                .chargedDateTime(historyEntity.getCreateDateTime())
                .build();
    }

    private MemberEntity getMemberEntity(PointChargeRequest request) {
        MemberEntity memberEntity = memberJpaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PointServiceException(MEMBER_NOT_FOUND));
        return memberEntity;
    }

    private void validateDuplicateCharge(PointChargeRequest request) {
        if (pointHistoryJpaRepository.existsByPaymentKey(request.getPaymentKey())) {
            throw new PointServiceException(POINT_ALREADY_CHARGED);
        }
    }

    private PaymentApprovalVerifier getPaymentApprovalVerifier(PointChargeRequest request) {
        PaymentApprovalVerifier verifier = paymentApprovalVerifier.get(request.getPgType());
        if (verifier == null) {
            throw new PointServiceException(PG_CLIENT_NOT_FOUND);
        }
        return verifier;
    }

    private PaymentApprovalResult verifyPayment(PointChargeRequest request, PaymentApprovalVerifier verifier) {
        PaymentApprovalResult result = verifier.verifyApproved(request.getPaymentKey());
        if (!result.isSuccess()) {
            throw new PointServiceException(PAYMENT_KEY_INVALID);
        }
        return result;
    }

    private PointWalletEntity updatePointWallet(PointChargeRequest request, MemberEntity memberEntity,
            PaymentApprovalResult paymentApprovalResult) {
        PointWalletEntity pointWalletEntity = pointWalletJpaRepository.findByMemberEntityEmail(request.getEmail())
                .orElse(new PointWalletEntity(memberEntity, BigDecimal.ZERO));

        pointWalletEntity.charge(paymentApprovalResult.getApprovedAmount());
        return pointWalletJpaRepository.save(pointWalletEntity);
    }

    private PointHistoryEntity savePointHistory(PointChargeRequest request, MemberEntity memberEntity,
            PaymentApprovalResult paymentApprovalResult) {
        PointHistoryEntity historyEntity = PointHistoryEntity.builder()
                .memberEntity(memberEntity)
                .amount(paymentApprovalResult.getApprovedAmount())
                .type(PointHistoryType.CHARGE)
                .paymentKey(request.getPaymentKey())
                .reason("")
                .build();
        return pointHistoryJpaRepository.save(historyEntity);
    }
}
