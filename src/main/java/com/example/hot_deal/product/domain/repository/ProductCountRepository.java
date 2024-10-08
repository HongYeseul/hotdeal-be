package com.example.hot_deal.product.domain.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Slf4j
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
        String script = """
            local stock = redis.call('GET', KEYS[1])
            if not stock then
              redis.call('SET', KEYS[1], ARGV[1])
              stock = ARGV[1]
            end
            if tonumber(stock) <= 0 then
              return -1
            end
            redis.call('DECR', KEYS[1])
            return tonumber(stock) - 1
            """;

        Long result = redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.eval(script.getBytes(), ReturnType.INTEGER, 1,
                        (KEY_PREFIX + productId).getBytes(), initialStock.getBytes()));

        if (result == -1L) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        log.info("사용자가 상품 {}를 주문했습니다. 남은 재고: {}", productId, result);
        return result;
    }

    public Long getStock(String productId) {
        return executeWithKey(productId, key -> {
            String stock = redisTemplate.opsForValue().get(key);
            return stock != null ? Long.parseLong(stock) : null;
        });
    }

    public Long getOrSetStock(String productId, Long stock) {
        return executeWithKey(productId, key -> {
            String result = redisTemplate.opsForValue().get(key);
            if (result == null) {
                redisTemplate.opsForValue().set(key, stock.toString());
                return stock;
            }
            return Long.parseLong(result);
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
