package com.example.hot_deal.payment.domain;

import jakarta.persistence.Column;

import java.util.UUID;

public class PaymentKey {

    @Column(unique = true, nullable = false)
    private final UUID key;

    public PaymentKey(UUID key) {
        this.key = key;
    }
}
