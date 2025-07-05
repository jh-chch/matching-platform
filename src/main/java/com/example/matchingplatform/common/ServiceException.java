package com.example.matchingplatform.common;

import org.springframework.http.HttpStatus;

public abstract class ServiceException extends RuntimeException {
    public abstract String getMessage(); // 기본 메시지

    public abstract HttpStatus getHttpStatus(); // 상태 코드

    public abstract String getErrorCode(); // 필요 시 에러 코드

    @Override
    public String toString() {
        return String.format("[%s] %s", getErrorCode(), getMessage());
    }
}
