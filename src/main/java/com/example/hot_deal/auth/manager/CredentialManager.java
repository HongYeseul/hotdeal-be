package com.example.hot_deal.auth.manager;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.provider.CookieProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialManager {

    private final CookieProvider cookieProvider;

//    public void setCredential(MemberTokens tokens, HttpServletResponse httpServletResponse) {
//        ResponseCookie accessTokenCookie = addCookieToResponse(tokens.accessToken(), ACCESS_TOKEN);
//
//        httpServletResponse.setHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + accessTokenCookie.toString());
//        Cookie refreshCookie = cookieProvider.createCookieForToken(tokens.refreshToken(), REFRESH_TOKEN);
//        httpServletResponse.addCookie(refreshCookie);
//    }

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
