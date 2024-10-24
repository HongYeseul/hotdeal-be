package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.member.domain.entity.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthProvider {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    public MemberTokens generateToken(Member member) {
        String accessToken = generateAccessToken(member);
        String refreshToken = generateRefreshToken(member);

        Cookie refreshTokenCookie = cookieProvider.createCookieForToken(refreshToken, TokenType.REFRESH_TOKEN);
        return new MemberTokens(accessToken, refreshTokenCookie);
    }

//    public MemberTokens makeToken(Member member) {
//        return new MemberTokens(
//                createAccessToken(member),
//                createRefreshToken(member)
//        );
//    }

    private String generateAccessToken(Member member) {
        return jwtProvider.createToken(member, TokenType.ACCESS_TOKEN);
    }

    private String generateRefreshToken(Member member) {
        return jwtProvider.createToken(member, TokenType.REFRESH_TOKEN);
    }
}
