package com.example.hot_deal.auth.dto;

public record MemberTokens(
        String accessToken,
        String refreshToken
) { }
