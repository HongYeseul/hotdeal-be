package com.example.hot_deal.member.dto;

import com.example.hot_deal.common.util.validation.annotation.ValidPassword;
import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청")
public class RegisterRequest extends BaseMemberDTO {

    @ValidPassword
    @Schema(description = "비밀번호는 영어, 숫자, 특수 문자를 하나 이상 포함해야 합니다.", example = "Test1234test!")
    private String rawPassword;

    public RegisterRequest(String email, String name, String rawPassword) {
        super(email, name);
        this.rawPassword = rawPassword;
    }

    public RegisterResponse toResponse() {
        return new RegisterResponse(
                super.getEmail(),
                super.getName()
        );
    }
}
