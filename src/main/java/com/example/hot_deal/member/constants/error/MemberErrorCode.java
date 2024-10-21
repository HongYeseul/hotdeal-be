package com.example.hot_deal.member.constants.error;

import com.example.hot_deal.common.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    // 400 - 잘못된 요청 (Bad Request)
    INCORRECT_PASSWORD(BAD_REQUEST, "입력된 비밀번호가 잘못되었습니다."),

    // 401 - 인증되지 않음 (Unauthorized)
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "인증되지 않은 접근입니다."),
    LOGIN_REQUIRED(UNAUTHORIZED, "서비스를 이용하시려면 로그인이 필요합니다."),

    // 403 - 금지됨 (Forbidden)
    ACCESS_FORBIDDEN(FORBIDDEN, "해당 리소스에 대한 접근 권한이 없습니다."),
    FEATURE_UNAVAILABLE(FORBIDDEN, "현재 기능을 일시적으로 사용할 수 없습니다."),

    // 404 - 리소스를 찾을 수 없음 (Not Found)
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // 409 - 리소스 충돌 (Conflict)
    DUPLICATE_EMAIL(CONFLICT, "중복된 로그인 아이디(이메일)가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
