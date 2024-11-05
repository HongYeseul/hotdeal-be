package com.example.hot_deal.payment.domain;

import com.example.hot_deal.common.domain.BaseTimeEntity;
import com.example.hot_deal.common.domain.Price;
import com.example.hot_deal.order.domain.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private Price totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;        // 결제 유형 (카드, 포인트 등)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;    // 결제 상태

    public Payment(Order order, Price totalPrice, PaymentType type) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.paymentType = type;
        this.paymentStatus = PaymentStatus.PAYMENT_PENDING;
    }
}
