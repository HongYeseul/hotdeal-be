package com.example.hot_deal.product.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Long decrement(String productId) {
        return redisTemplate.opsForValue().decrement(productId);
    }

    public Long getStock(String productId) {
        String stock = redisTemplate.opsForValue().get(productId);
        return stock != null ? Long.parseLong(stock) : null;
    }

    public void setStock(String productId, Long stock) {
        redisTemplate.opsForValue().set(productId, stock.toString());
    }
}
