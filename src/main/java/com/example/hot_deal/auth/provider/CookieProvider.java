package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.constants.TokenType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

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


    public Optional<Cookie> getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst();
    }

    public Cookie expireCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
