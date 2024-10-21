package com.example.hot_deal.auth.constants.error;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    // 400 - 잘못된 요청 (Bad Request)
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰 값이 존재하지 않습니다."),
    TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 토큰입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    TOKEN_TYPE_INVALID(HttpStatus.BAD_REQUEST, "토큰 유형이 일치하지 않습니다."),
    TOKEN_MALFORMED(HttpStatus.BAD_REQUEST, "토큰이 손상되었습니다."),

    // 401 - 인증되지 않음 (Unauthorized)
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_SIGNATURE_FAILED(HttpStatus.UNAUTHORIZED, "토큰 서명 검증에 실패했습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "서비스를 이용하시려면 로그인이 필요합니다."),

    // 500 - 서버 오류 (Internal Server Error)
    INVALID_AUTHENTICATION_STATE(HttpStatus.INTERNAL_SERVER_ERROR, "인증 상태가 올바르지 않습니다. 관리자에게 문의하세요.");

    private final HttpStatus httpStatus;
    private final String message;
}

