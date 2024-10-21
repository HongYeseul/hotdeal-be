package com.example.hot_deal.auth.manager;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.dto.MemberTokens;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import static com.example.hot_deal.auth.constants.TokenType.ACCESS_TOKEN;
import static com.example.hot_deal.auth.constants.TokenType.REFRESH_TOKEN;

@Component
public class CredentialManager {

    public void setCredential(MemberTokens tokens, HttpServletResponse httpServletResponse) {
        ResponseCookie responseCookie = addCookieToResponse(tokens.accessToken(), ACCESS_TOKEN);
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        ResponseCookie refreshToken = addCookieToResponse(tokens.refreshToken(), REFRESH_TOKEN);
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());
    }

    public ResponseCookie addCookieToResponse(String token, TokenType tokenType) {
        return ResponseCookie.from(tokenType.name(), token)
                .maxAge(tokenType.getExpirationTime())
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
