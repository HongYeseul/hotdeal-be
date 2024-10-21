package com.example.hot_deal.member.dto;

import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 응답")
public class RegisterResponse extends BaseMemberDTO {

    public RegisterResponse(String email, String name) {
        super(email, name);
    }
}
