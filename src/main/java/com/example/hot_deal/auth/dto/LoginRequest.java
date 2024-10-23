package com.example.hot_deal.auth.dto;

import com.example.hot_deal.common.util.validation.annotation.ValidEmail;
import com.example.hot_deal.common.util.validation.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "로그인 요청")
public record LoginRequest (

        @ValidEmail
        @Schema(description = "회원 이메일", example = "test1234@naver.com")
        @Size(max = 255, message = "이메일은 255자 이하로 입력해주세요.")
        String loginId,

        @ValidPassword
        @Schema(description = "비밀번호는 영어, 숫자, 특수 문자를 하나 이상 포함해야 합니다.", example = "Test1234test!")
        String password
){
}
