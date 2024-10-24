package com.example.hot_deal.product.constants.error;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
