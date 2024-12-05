package com.example.hot_deal.common.config.web;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  ElementType.TYPE: 클래스, 인터페이스, enum에서 사용할 수 있는 애노테이션
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Component
public @interface ApiV1 {
}
