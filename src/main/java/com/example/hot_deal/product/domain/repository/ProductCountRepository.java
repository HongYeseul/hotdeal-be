package com.example.hot_deal.product.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class ProductCountRepository {
    private static final String KEY_PREFIX = "product:count:";

    private final RedisTemplate<String, String> redisTemplate;

    private <T> T executeWithKey(String productId, Function<String, T> operation) {
        String key = KEY_PREFIX + productId;
        return operation.apply(key);
    }

    public Long decrement(String productId, String initialStock) {
        return executeWithKey(productId, key -> {
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
        });
    }

    public Long getStock(String productId) {
        return executeWithKey(productId, key -> {
            String stock = redisTemplate.opsForValue().get(key);
            return stock != null ? Long.parseLong(stock) : null;
        });
    }

    public Long setStockIfNotExists(String productId, Long stock) {
        return executeWithKey(productId, key -> {
            Boolean wasAbsent = redisTemplate.opsForValue().setIfAbsent(key, stock.toString());
            if (Boolean.TRUE.equals(wasAbsent)) {
                return stock;
            } else {
                String currentStock = redisTemplate.opsForValue().get(key);
                return currentStock != null ? Long.parseLong(currentStock) : null;
            }
        });
    }
}
