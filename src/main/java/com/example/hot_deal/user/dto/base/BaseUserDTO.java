package com.example.hot_deal.user.dto.base;

import com.example.hot_deal.common.util.validation.annotation.ValidEmail;
import com.example.hot_deal.common.util.validation.annotation.ValidName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원 기본 정보")
public class BaseUserDTO {
    @ValidEmail
    private final String email;

    @ValidName
    private final String name;

}
