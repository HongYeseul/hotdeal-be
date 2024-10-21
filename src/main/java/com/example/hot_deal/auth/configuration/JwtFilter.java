package com.example.hot_deal.auth.configuration;

import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.auth.provider.CookieProvider;
import com.example.hot_deal.auth.provider.JwtProvider;
import com.example.hot_deal.member.domain.entity.Member;
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
        Cookie cookie = cookieProvider.getCookieByName(request, TokenType.ACCESS_TOKEN.name());

        if (authorization != null && authorization.startsWith(BEARER_TOKEN_PREFIX)) {
            String accessToken = authorization.substring(BEARER_TOKEN_PREFIX.length());
            jwtProvider.validateToken(accessToken, TokenType.ACCESS_TOKEN);
            setAuthentication(accessToken);
        } else if (cookie != null && authorization == null) {
            String refreshToken = cookie.getValue();
            jwtProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN);
            setAuthentication(refreshToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        Long id = jwtProvider.getId(token);
        Member member = Member.builder()
                .id(id)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

