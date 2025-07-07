package com.example.matchingplatform.payment.service;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PointServiceErrorCode {
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POINT_ALREADY_CHARGED("이미 적립된 결제건 입니다.", HttpStatus.BAD_REQUEST),
    PG_CLIENT_NOT_FOUND("지원하지 않는 PG사 입니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_KEY_INVALID("유효하지 않은 결제키", HttpStatus.BAD_REQUEST),
    POINT_CHARGE_CONCURRENCY_FAIL("포인트 충전 실패 - 재시도 초과", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
