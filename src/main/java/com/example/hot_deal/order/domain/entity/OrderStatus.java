package com.example.hot_deal.order.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    WAIT_FOR_PAYMENT("주문 대기 중"),
    ORDER_FAILED("주문 실패"),
    PREPARING_FOR_SHIPMENT("배송 준비 중"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료");

    private final String description;

}
