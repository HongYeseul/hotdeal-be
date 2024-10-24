package com.example.hot_deal.auth.configuration;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.provider.CookieProvider;
import com.example.hot_deal.auth.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.example.hot_deal.auth.constants.JwtConstants.AUTHORIZATION_HEADER;
import static com.example.hot_deal.auth.constants.JwtConstants.BEARER_TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        Optional<Cookie> refreshTokenCookie = cookieProvider.getCookieByName(request, TokenType.REFRESH_TOKEN.name());

        if (authorization != null && authorization.startsWith(BEARER_TOKEN_PREFIX)) {
            String accessToken = authorization.substring(BEARER_TOKEN_PREFIX.length());
            jwtProvider.validateToken(accessToken, TokenType.ACCESS_TOKEN);
            setAuthentication(accessToken);
        } else if (refreshTokenCookie.isPresent() && authorization == null) {
            // 쿠키가 존재하고 authorization 헤더가 없을 때
            String refreshToken = refreshTokenCookie.get().getValue();
            jwtProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN);
            setAuthentication(refreshToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        MemberInfo memberInfo = jwtProvider.getMemberInfo(token);
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                memberInfo,
                null,
                memberInfo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

