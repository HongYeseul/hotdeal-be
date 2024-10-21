package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthProvider {

    private final JwtProvider jwtProvider;

    public MemberTokens makeToken(Member member) {
        return new MemberTokens(
                createAccessToken(member),
                createRefreshToken(member)
        );
    }

    public String createAccessToken(Member member) {
        return jwtProvider.createToken(member, TokenType.ACCESS_TOKEN);
    }

    public String createRefreshToken(Member member) {
        return jwtProvider.createToken(member, TokenType.REFRESH_TOKEN);
    }
}
