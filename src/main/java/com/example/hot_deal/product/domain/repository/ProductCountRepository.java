package com.example.hot_deal.product.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCountRepository {
    private static final String KEY_PREFIX = "product:count:";

    private final RedisTemplate<String, String> redisTemplate;

    public void decrement(String productId, String initialStock) {
        String key = KEY_PREFIX + productId;
        String stock = redisTemplate.opsForValue().get(key);
        if (stock == null) {
            redisTemplate.opsForValue().set(key, initialStock);
            stock = initialStock;
        }

        long currentStock = Long.parseLong(stock);
        if (currentStock <= 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        redisTemplate.opsForValue().decrement(key);
    }

    public Long getStock(String productId) {
        String stock = redisTemplate.opsForValue().get(productId);
        return stock != null ? Long.parseLong(stock) : null;
    }

    public void setStock(String productId, Long stock) {
        redisTemplate.opsForValue().set(productId, stock.toString());
    }
}
