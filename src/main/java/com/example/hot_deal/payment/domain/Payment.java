package com.example.hot_deal.payment.domain;

import com.example.hot_deal.common.domain.BaseTimeEntity;
import com.example.hot_deal.common.domain.Price;
import com.example.hot_deal.member.domain.entity.Member;
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

import java.util.UUID;

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
    private PaymentKey paymentKey;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member payedMember;

    @Embedded
    private Price totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType method;     // 결제 유형 (카드, 가상계좌, 간편결제 등)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;   // 결제 상태

    public Payment(Order order, Member member, Price totalAmount, PaymentType type, PaymentStatus status) {
        this.order = order;
        this.paymentKey = new PaymentKey(UUID.randomUUID());
        this.payedMember = member;
        this.totalAmount = totalAmount;
        this.method = type;
        this.status = status;
    }
}
