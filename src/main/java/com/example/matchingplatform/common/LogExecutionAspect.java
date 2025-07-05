package com.example.matchingplatform.common;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogExecutionAspect {

    private static final String TRACE_ID = "traceId";

    @Around("@annotation(logExecution)")
    public Object logExecution(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable {
        String traceId = MDC.get(TRACE_ID);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String classMethod = signature.getDeclaringType().getSimpleName() + "." + signature.getName();
        String description = logExecution.value();

        log.info("[{}] → 요청: {} | traceId={} | args={}", description, classMethod, traceId,
                Arrays.toString(joinPoint.getArgs()));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;

            log.info("[{}] ← 응답: {} | traceId={} | result={} | 처리시간={}ms",
                    description, classMethod, traceId, result, elapsed);
            return result;
        } catch (Throwable t) {
            long elapsed = System.currentTimeMillis() - start;
            log.info("[{}] 예외: {} | traceId={} | message={} | 처리시간={}ms",
                    description, classMethod, traceId, t.getMessage(), elapsed);
            throw t;
        }
    }
}
