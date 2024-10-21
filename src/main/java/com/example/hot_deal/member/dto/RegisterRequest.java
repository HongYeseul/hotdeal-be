package com.example.hot_deal.member.dto;

import com.example.hot_deal.common.util.validation.annotation.ValidPassword;
import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청")
public class RegisterRequest extends BaseMemberDTO {

    @ValidPassword
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
