package com.example.hot_deal.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public final class PaymentKey {

    @Column(unique = true, nullable = false)
    private final UUID key;

    public PaymentKey() {
        this.key = UUID.randomUUID();
    }
}
