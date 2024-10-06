package com.example.hot_deal.product.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCountRepository {
    private static final String KEY_PREFIX = "product:count:";

    private final RedisTemplate<String, String> redisTemplate;

    public Long decrement(String productId, String initialStock) {
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

        return redisTemplate.opsForValue().decrement(key);
    }

    public Long getStock(String productId) {
        String key = KEY_PREFIX + productId;
        String stock = redisTemplate.opsForValue().get(key);
        return stock != null ? Long.parseLong(stock) : null;
    }

    public Long setStockIfNotExists(String productId, Long stock) {
        String key = KEY_PREFIX + productId;
        Boolean wasAbsent = redisTemplate.opsForValue().setIfAbsent(key, stock.toString());
        if (Boolean.TRUE.equals(wasAbsent)) {
            return stock;
        } else {
            return getStock(productId);
        }
    }
}
