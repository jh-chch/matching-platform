package com.example.matchingplatform.member.service;

import org.springframework.http.HttpStatus;

import com.example.matchingplatform.common.ServiceException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberServiceException extends ServiceException {
    private final MemberServiceErrorCode errorCode;

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