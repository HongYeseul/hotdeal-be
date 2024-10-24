package com.example.hot_deal.order.consumer;

import lombok.Getter;

@Getter
public class PurchasedRequest {
    private final Long memberId;
    private final Long productId;

    private PurchasedRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public PurchasedRequest(String message) {
        String[] parts = message.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 메시지 형식입니다.");
        }

        this.memberId = Long.parseLong(parts[0]);
        this.productId = Long.parseLong(parts[1]);
    }
}
