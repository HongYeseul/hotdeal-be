package com.example.hot_deal.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) { }
