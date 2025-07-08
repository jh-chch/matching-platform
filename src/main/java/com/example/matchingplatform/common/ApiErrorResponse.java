package com.example.matchingplatform.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String errorCode;
    private final String message;

    @Builder
    private ApiErrorResponse(LocalDateTime timestamp, int status, String errorCode, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, String errorCode, String message) {
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
