package com.example.hot_deal.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    INVALID_NUMBER_FORMAT(NOT_FOUND, "숫자 형식이 잘못되었습니다."),

    // 400 - 잘못된 요청 (Bad Request)
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "금액은 0 이상의 양수여야 합니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "수량은 양수여야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
