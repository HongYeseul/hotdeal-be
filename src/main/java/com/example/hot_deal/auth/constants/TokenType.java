package com.example.hot_deal.auth.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN(600), // 10분
    REFRESH_TOKEN(1800); // 30분

    private final int expirationTime;
}
