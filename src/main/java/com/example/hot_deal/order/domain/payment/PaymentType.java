package com.example.hot_deal.order.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    CARD("카드 결제"),
    POINT("포인트 결제"),
    TRANSFER("계좌이체"),
    MOBILE_PAY("간편 결제");

    private final String description;
}