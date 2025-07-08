package com.example.matchingplatform.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceException(ServiceException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiErrorResponse.of(e.getHttpStatus(), e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                        "서버 내부 오류가 발생했습니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleEnumBinding(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된1 요청입니다."));
    }
}
