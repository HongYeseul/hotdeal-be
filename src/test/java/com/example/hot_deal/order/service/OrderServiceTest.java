package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductCountRepository;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    private static final String TEST_USER_NAME = "Hong";
    private static final String TEST_USER_EMAIL = "hong@gmail.com";
    private static final String TEST_USER_PASSWORD = "test123";
    private static final String TEST_PRODUCT_NAME = "PRODUCT";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final Long TEST_PRODUCT_STOCK = 100L;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductCountRepository productCountRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private Long userId;
    private Long productId;

    @BeforeEach
    void setUp() {
        User user = createTestUser();
        userRepository.save(user);
        userId = user.getId();

        Product product = createTestProduct();
        productRepository.save(product);
        productId = product.getId();

        redisTemplate.opsForValue().set(productId.toString(), TEST_PRODUCT_STOCK.toString());
    }

    private User createTestUser() {
        return User.builder()
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
        userRepository.deleteAll();
        redisTemplate.delete(productId.toString());
    }

    @Test
    public void 한번_구매() {
        orderService.orderProduct(userId, productId);

        // Then
        assertEquals(99L, Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(productId.toString()))));
        assertEquals(1, orderRepository.count());
    }

    @Test
    public void 상품_주문시_재고가_감소하고_주문이_생성된다() {
        // Given
        User user = userRepository.findAll().get(0);
        Product product = productRepository.findAll().get(0);
        Long initialStock = product.getStockQuantity();

        // When
        orderService.orderProduct(user.getId(), product.getId());

        // Then
        assertEquals(initialStock - 1, Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(product.getId().toString()))));

        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
        Order createdOrder = orders.get(0);
        assertEquals(user.getId(), createdOrder.getUser().getId());
        assertEquals(product.getId(), createdOrder.getProduct().getId());
    }
}