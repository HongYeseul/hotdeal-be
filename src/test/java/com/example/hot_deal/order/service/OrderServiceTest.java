package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    private static final String TEST_USER_NAME = "Hong";
    private static final String TEST_USER_EMAIL = "hong@gmail.com";
    private static final String TEST_USER_PASSWORD = "test123";
    private static final String TEST_PRODUCT_NAME = "PRODUCT";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final Long TEST_PRODUCT_STOCK = 100L;
    private static final String KEY_PREFIX = "product:count:";

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberJpaRepository userJpaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Long userId;
    private Long productId;

    @BeforeEach
    void setUp() {
        Member member = createTestUser();
        userJpaRepository.save(member);
        userId = member.getId();
    
        Product product = createTestProduct();
        productRepository.save(product);
        productId = product.getId();
    
        redisTemplate.opsForValue().set(KEY_PREFIX + productId.toString(), TEST_PRODUCT_STOCK.toString());
    }

    private Member createTestUser() {
        return Member.builder()
                .name(TEST_USER_NAME)
                .email(TEST_USER_EMAIL)
                .passwordHash(TEST_USER_PASSWORD)
                .build();
    }

    private Product createTestProduct() {
        return Product.builder()
                .name(TEST_PRODUCT_NAME)
                .price(TEST_PRODUCT_PRICE)
                .stockQuantity(TEST_PRODUCT_STOCK)
                .build();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userJpaRepository.deleteAll();
        redisTemplate.delete(productId.toString());
    }

    @Test
    public void 동시에_100개의_구매_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.orderProduct(userId, productId);
                } catch (Exception e) {
                    log.error("주문 처리 중 오류 발생: {}", e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        Thread.sleep(5000);

        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            log.error("ExecutorService가 제한 시간 내에 종료되지 않았습니다.");
        }

        // Redis 재고 확인
        String stockStr = redisTemplate.opsForValue().get(KEY_PREFIX + productId.toString());
        assertNotNull(stockStr, "Redis의 재고가 null입니다.");
        long redisStock = Long.parseLong(stockStr);
        
        // DB 재고 확인
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        long dbStock = updatedProduct.getStockQuantity();
        
        // 주문 수 확인
        long orderCount = orderRepository.count();

        log.info("Redis 재고: {}", redisStock);
        log.info("DB 재고: {}", dbStock);
        log.info("주문 수: {}", orderCount);

        assertEquals(0L, redisStock, "Redis 재고가 0이 아닙니다.");
        assertEquals(0L, dbStock, "DB 재고가 0이 아닙니다.");
        assertEquals(100L, orderCount, "주문 수가 100개가 아닙니다.");
    }

    @Test
    public void 한번_구매() throws InterruptedException {
        orderService.orderProduct(userId, productId);

        // Then
        String stockStr = redisTemplate.opsForValue().get(KEY_PREFIX + productId.toString());

        Thread.sleep(3000);

        assertNotNull(stockStr, "재고가 null입니다.");
        assertEquals(99L, Long.parseLong(stockStr));
        assertEquals(1, orderRepository.count());

        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        assertEquals(99L, updatedProduct.getStockQuantity());
    }

    @Test
    public void 상품_주문시_재고가_감소하고_주문이_생성된다() throws InterruptedException {
        // Given
        Member member = userJpaRepository.findAll().get(0);
        Product product = productRepository.findAll().get(0);
        Long initialStock = product.getStockQuantity();

        // When
        orderService.orderProduct(member.getId(), product.getId());
        Thread.sleep(3000);

        // Then
        assertEquals(initialStock - 1, Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(KEY_PREFIX + product.getId().toString()))));

        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
        Order createdOrder = orders.get(0);
        assertEquals(member.getId(), createdOrder.getMember().getId());
        assertEquals(product.getId(), createdOrder.getProduct().getId());
    }
}