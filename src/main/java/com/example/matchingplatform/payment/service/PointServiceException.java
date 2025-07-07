package com.example.matchingplatform.payment.service;

import org.springframework.http.HttpStatus;

import com.example.matchingplatform.common.ServiceException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointServiceException extends ServiceException {
    private final PointServiceErrorCode errorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }

    @Override
    public String getErrorCode() {
        return errorCode.name();
    }
}
