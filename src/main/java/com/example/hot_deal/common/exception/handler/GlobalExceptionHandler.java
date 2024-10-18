package com.example.hot_deal.common.exception.handler;

import com.example.hot_deal.common.exception.HotDealException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * API 실패 예외
     */
    @ExceptionHandler(HotDealException.class)
    public ResponseEntity<ProblemDetail> handleHotDealException(HotDealException hotDealException) {
        log.warn("[HotDealException] {}가 발생했습니다.", hotDealException.getClass().getName(), hotDealException);
        return ResponseEntity.status(hotDealException.getErrorCode().getHttpStatus())
                .body(ProblemDetail.forStatusAndDetail(
                        hotDealException.getErrorCode().getHttpStatus(),
                        hotDealException.getMessage())
                );
    }

    /**
     * 유효성 검사 실패 예외
     * 예외 메시지(detail) 예시: "유효하지 않은 비밀번호 형식입니다.\n 유효하지 않은 이메일 형식입니다."
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        List<String> errorMessages = exception.getBindingResult()
                .getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        log.warn("[MethodArgumentNotValidException] {}가 발생했습니다.", errorMessages.getClass().getName());
        log.warn("Full exception details:", exception);

        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        String.join("\n ", errorMessages))
                );
    }
}
