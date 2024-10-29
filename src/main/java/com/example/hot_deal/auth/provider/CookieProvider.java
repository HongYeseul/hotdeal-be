package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.common.exception.HotDealException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

import static com.example.hot_deal.auth.constants.error.AuthErrorCode.COOKIE_NOT_FOUND;

@Component
public class CookieProvider {

    public Cookie createCookieForToken(String token, TokenType tokenType) {
        Cookie cookie = new Cookie(tokenType.name(), token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(tokenType.getExpirationTime());
        cookie.setPath("/");
        return cookie;
    }

    public String getCookie(HttpServletRequest request, TokenType tokenType) {
        return getCookieByName(request, tokenType)
                .orElseThrow(() -> new HotDealException(COOKIE_NOT_FOUND))
                .getValue();
    }

    public Optional<Cookie> getCookieByName(HttpServletRequest request, TokenType tokenType) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> tokenType.name().equals(cookie.getName()))
                .findFirst();
    }

    public void expireCookie(HttpServletResponse response, TokenType tokenType) {
        ResponseCookie cookie = ResponseCookie.from(tokenType.name(), null)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
