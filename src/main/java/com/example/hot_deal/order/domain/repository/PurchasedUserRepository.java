package com.example.hot_deal.order.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchasedUserRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void purchase(Long userId, Long productId) {
        String key = userId.toString();
        String value = userId + ":" + productId;
        kafkaTemplate.send("applied-users", key, value);
    }
}
