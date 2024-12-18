package com.example.hot_deal.order.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import static com.example.hot_deal.common.constants.KafkaConstants.PRODUCT_ORDER_TOPIC;

@Repository
@RequiredArgsConstructor
public class PurchasedUserRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 구매 요청 Topic 에 메시지 전달
     * @param userId 구매한 회원
     * @param productId 구매 제품
     */
    public void purchase(Long userId, Long productId) {
        String key = userId.toString();
        String value = userId + ":" + productId;
        kafkaTemplate.send(PRODUCT_ORDER_TOPIC, key, value);
    }
}
