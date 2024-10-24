package com.example.hot_deal.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    INVALID_NUMBER_FORMAT(NOT_FOUND, "숫자 형식이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
