package com.example.hot_deal.product.constants.error;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다."),

    // 400 - 잘못된 요청 (Bad Request)
    INVALID_PRODUCT_PRICE(BAD_REQUEST, "상품 가격이 유효하지 않습니다."),
    INVALID_PRODUCT_QUANTITY(BAD_REQUEST, "상품 수량이 유효하지 않습니다."),

    // 409 - 충돌 (Conflict)
    PRODUCT_ALREADY_EXISTS(CONFLICT, "이미 존재하는 상품입니다."),
    INSUFFICIENT_PRODUCT_QUANTITY(CONFLICT, "수량이 부족하여 감소할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
