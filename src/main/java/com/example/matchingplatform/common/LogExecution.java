package com.example.matchingplatform.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 실행 전후에 로그를 남기기 위한 어노테이션
 * 어노테이션이 붙은 메서드는 LogExecutionAspect에서 처리
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExecution {
    String value() default ""; // 로그 메시지 설명(선택)
}
