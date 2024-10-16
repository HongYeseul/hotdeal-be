package com.example.hot_deal.common.exception.handler;

import com.example.hot_deal.common.exception.ErrorResponse;
import com.example.hot_deal.common.exception.HotDealException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * API 유효성 검사 실패 예외
     */
    @ExceptionHandler(HotDealException.class)
    public ResponseEntity<ErrorResponse> handleHotDealException(HotDealException e) {
        ErrorResponse errorResponse = ErrorResponse.from(e.getErrorCode().getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * 유효성 검사 실패 예외
     * errorMessage 예시: "유효성 검사 실패: name: 이름은 비어 있을 수 없습니다., email: 유효한 이메일 주소를 입력해주세요."
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);
        ErrorResponse errorResponse = ErrorResponse.from(BAD_REQUEST, "유효성 검사 실패: " + errorMessage);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }
}
