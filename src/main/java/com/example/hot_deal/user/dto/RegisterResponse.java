package com.example.hot_deal.user.dto;

import com.example.hot_deal.user.dto.base.BaseUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 응답")
public class RegisterResponse extends BaseUserDTO {

    public RegisterResponse(String email, String name) {
        super(email, name);
    }
}
