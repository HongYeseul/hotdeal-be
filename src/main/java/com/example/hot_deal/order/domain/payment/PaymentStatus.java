package com.example.hot_deal.order.domain.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PAYMENT_PENDING("결제 대기 중"),
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAILED("결제 실패"),
    REFUND_REQUESTED("환불 요청 중"),
    REFUNDED("환불 완료");

    private final String description;

}

