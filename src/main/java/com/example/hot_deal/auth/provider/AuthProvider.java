package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.member.domain.entity.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.hot_deal.auth.constants.JwtConstants.AUTHORIZATION_HEADER;
import static com.example.hot_deal.auth.constants.JwtConstants.BEARER_TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class AuthProvider {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    /**
     * AccessToken, RefreshToken 저장
     * AccessToken: 헤더
     * RefreshToken: 쿠키
     */
    public void setCredentials(MemberTokens tokens, HttpServletResponse response) {
        response.setHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + tokens.accessToken());
        response.addCookie(tokens.refreshToken());
    }

    /**
     * AccessToken, RefreshToken 값 생성
     * @return AccessToken(String), RefreshToken(Cookie)
     */
    public MemberTokens generateToken(Member member) {
        String accessToken = generateAccessToken(member);
        String refreshToken = generateRefreshToken(member);

        Cookie refreshTokenCookie = cookieProvider.createCookieForToken(refreshToken, TokenType.REFRESH_TOKEN);
        return new MemberTokens(accessToken, refreshTokenCookie);
    }

    private String generateAccessToken(Member member) {
        return jwtProvider.createToken(member, TokenType.ACCESS_TOKEN);
    }

    private String generateRefreshToken(Member member) {
        return jwtProvider.createToken(member, TokenType.REFRESH_TOKEN);
    }

    /**
     * RefreshToken 값 검색
     */
    public String getRefreshToken(HttpServletRequest request) {
        return cookieProvider.getCookie(request, TokenType.REFRESH_TOKEN);
    }

    /**
     * RefreshToken 쿠키 삭제
     */
    public void deleteRefreshToken(HttpServletResponse response) {
        cookieProvider.expireCookie(response, TokenType.REFRESH_TOKEN);
    }
}
