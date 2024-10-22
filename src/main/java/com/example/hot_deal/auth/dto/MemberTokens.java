package com.example.hot_deal.auth.dto;

import jakarta.servlet.http.Cookie;

public record MemberTokens(
        String accessToken,
        Cookie refreshToken
) { }
