package com.example.hot_deal.payment.domain;

/**
 * 토스 기준 결제 상태
 * @docs https://docs.tosspayments.com/reference#paymenttransactiondto-status
 */
public enum PaymentStatus {
    READY,
    IN_PROGRESS,
    WAITING_FOR_DEPOSIT,
    DONE,
    CANCELED,
    PARTIAL_CANCELED,
    ABORTED,
    EXPIRED
}

