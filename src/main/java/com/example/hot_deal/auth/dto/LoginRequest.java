package com.example.hot_deal.auth.dto;

import com.example.hot_deal.common.util.validation.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "로그인 요청")
public record LoginRequest (

        @Schema(description = "아이디", example = "code")
        @NotBlank(message = "아이디가 입력되지 않았습니다.")
        @Size(max = 255, message = "이메일은 255자 이하로 입력해주세요.")
        String loginId,

        @ValidPassword
        @Schema(description = "비밀번호는 영어 대소문자, 숫자, 특수 문자를 반드시 포함해야 합니다.", example = "Test1234test!")
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        @Size(min = 10, max = 255, message = "비밀번호는 10~20자 사이로 입력해주세요.")
        String password
){
}
