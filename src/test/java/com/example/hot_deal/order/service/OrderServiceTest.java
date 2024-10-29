package com.example.hot_deal.order.service;

import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.fixture.MemberFixture;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.product.domain.repository.ProductRepository;

import com.example.hot_deal.fixture.ProductFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    private static final String KEY_PREFIX = "product:count:";

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("구매 요청 테스트")
    class OrderTest {

        @Test
        @DisplayName("구매 요청 성공: 1번의 구매")
        void order() {
            Member member = memberRepository.save(MemberFixture.plainMemberFixture());
            Product product = productRepository.save(ProductFixture.productFixture());

            // Redis에 초기 재고 설정
            String productKey = KEY_PREFIX + product.getId();
            String initialStock = String.valueOf(product.getQuantity());
            redisTemplate.opsForValue().set(productKey, initialStock);

            orderService.orderProduct(member.getId(), product.getId());

            await().atMost(3, TimeUnit.SECONDS)
                    .until(() -> {
                        String currentStock = redisTemplate.opsForValue().get(productKey);
                        return currentStock != null;
                    });

            Product updatedProduct = productRepository.getProductById(product.getId());
            assertEquals(product.getQuantity() - 1L,
                        updatedProduct.getQuantity());
                
            redisTemplate.delete(productKey);
        }

        @Test
        @DisplayName("구매 요청 성공: 동시에 100번의 구매")
        public void order_concurrent100Request_Success() throws InterruptedException {
            Member member = memberRepository.save(MemberFixture.plainMemberFixture());
            Product product = productRepository.save(ProductFixture.productFixture());
            int threadCount = 100;
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService = Executors.newFixedThreadPool(processors * 2);

            CountDownLatch countDownLatch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        orderService.orderProduct(member.getId(), product.getId());
                    } catch (Exception e) {
                        log.error("주문 처리 중 오류 발생: {}", e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            countDownLatch.await();

            await()
                    .atMost(5, TimeUnit.SECONDS)
                    .pollInterval(100, TimeUnit.MILLISECONDS)
                    .until(() -> {
                        Product p = productRepository.getProductById(product.getId());
                        return p.getQuantity() == 0;
                    });

            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.error("ExecutorService가 제한 시간 내에 종료되지 않았습니다.");
            }

            // Redis 재고 확인
            String stockStr = redisTemplate.opsForValue().get(KEY_PREFIX + product.getId().toString());
            assertNotNull(stockStr, "Redis의 재고가 null입니다.");
            long redisStock = Long.parseLong(stockStr);

            // 주문 수 확인
            long orderCount = orderRepository.count();

            log.info("Redis 재고: {}", redisStock);
            log.info("주문 수: {}", orderCount);

            assertEquals(0L, redisStock, "Redis 재고가 0이 아닙니다.");
            assertEquals(100L, orderCount, "주문 수가 100개가 아닙니다.");
        }
    }
}
