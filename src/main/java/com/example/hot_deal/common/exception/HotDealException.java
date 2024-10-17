package com.example.hot_deal.common.exception;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class HotDealException extends RuntimeException {
    private final ErrorCode errorCode;

    public HotDealException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
