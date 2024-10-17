package com.example.hot_deal.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse (
        HttpStatus status,
        String message
) {
    public static ErrorResponse from(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }
}
