package com.example.hot_deal.order.constants.error;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    INVALID_MESSAGE_FORMAT(NOT_FOUND, "잘못된 주문 형식이 사용되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}