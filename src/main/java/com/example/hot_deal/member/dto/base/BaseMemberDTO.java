package com.example.hot_deal.member.dto.base;

import com.example.hot_deal.common.util.validation.annotation.ValidEmail;
import com.example.hot_deal.common.util.validation.annotation.ValidName;
import com.example.hot_deal.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원 기본 정보")
public class BaseMemberDTO {
    @ValidEmail
    @Schema(description = "회원 이메일", example = "test1234@naver.com")
    private final String email;

    @ValidName
    @Schema(description = "회원 이름", example = "홍길동")
    private final String name;

    public static BaseMemberDTO from(Member member) {
        return new BaseMemberDTO(member.getEmail(), member.getName());
    }
}
