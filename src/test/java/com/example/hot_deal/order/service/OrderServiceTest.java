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

import static org.junit.jupiter.api.Assertions.*;

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
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("구매 요청 테스트")
    class OrderTest {

        @Test
        @DisplayName("구매 요청 성공: 1번의 구매")
        void order() throws InterruptedException {
            Member member = memberRepository.save(MemberFixture.memberFixture());
            Product product = productRepository.save(ProductFixture.productFixture());

            redisTemplate.opsForValue().set(KEY_PREFIX + product.getId(), product.getStockQuantity().toString());

            orderService.orderProduct(member.getId(), product.getId());

            // Then
            redisTemplate.opsForValue().get(KEY_PREFIX + product.getId());

            Thread.sleep(3000);

            Product updatedProduct = productRepository.getProductById(product.getId());
            assertEquals(product.getStockQuantity()- 1L, updatedProduct.getStockQuantity());
            redisTemplate.delete(product.getId().toString());
        }
    }

//    @Test
//    public void 동시에_100개의_구매_요청() throws InterruptedException {
//        int threadCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
//
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    orderService.orderProduct(userId, productId);
//                } catch (Exception e) {
//                    log.error("주문 처리 중 오류 발생: {}", e.getMessage());
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//
//        countDownLatch.await();
//
//        Thread.sleep(5000);
//
//        executorService.shutdown();
//        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//            log.error("ExecutorService가 제한 시간 내에 종료되지 않았습니다.");
//        }
//
//        // Redis 재고 확인
//        String stockStr = redisTemplate.opsForValue().get(KEY_PREFIX + productId.toString());
//        assertNotNull(stockStr, "Redis의 재고가 null입니다.");
//        long redisStock = Long.parseLong(stockStr);
//
//        // DB 재고 확인
//        Product updatedProduct = productRepository.findById(productId).orElseThrow();
//        long dbStock = updatedProduct.getStockQuantity();
//
//        // 주문 수 확인
//        long orderCount = orderRepository.count();
//
//        log.info("Redis 재고: {}", redisStock);
//        log.info("DB 재고: {}", dbStock);
//        log.info("주문 수: {}", orderCount);
//
//        assertEquals(0L, redisStock, "Redis 재고가 0이 아닙니다.");
//        assertEquals(0L, dbStock, "DB 재고가 0이 아닙니다.");
//        assertEquals(100L, orderCount, "주문 수가 100개가 아닙니다.");
//    }
}