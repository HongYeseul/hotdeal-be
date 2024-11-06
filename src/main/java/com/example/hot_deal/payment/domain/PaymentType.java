package com.example.hot_deal.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토스 기준 결제 종류
 * @docs: https://docs.tosspayments.com/reference#paymenttransactiondto-method
 */
@Getter
@AllArgsConstructor
public enum PaymentType {
    CARD("카드"),
    TRANSFER("계좌이체"),
    MOBILE_PAY("간편결제"),
    VIRTUAL_ACCOUNT("가상계좌"),
    MOBILE("휴대폰"),
    CULTURE_GIFT_CARD("문화상품권"),
    BOOK_GIFT_CARD("도서문화상품권"),
    GAME_GIFT_CARD("게임문화상품권");

    private final String description;
}