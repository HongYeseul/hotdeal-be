package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Long userId;
    private Long productId;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("Hong")
                .email("hong@gmail.com")
                .passwordHash("test123")
                .build();
        user = userRepository.save(user);

        Product product = Product.builder()
                .name("PRODUCT")
                .price(BigDecimal.TEN)
                .stockQuantity(100L).build();
        productRepository.save(product);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 한번_구매() {
        orderService.orderProduct(1L, 1L);

        // Then
        Product product = productRepository.findById(1L).orElseThrow();
        assertEquals(99L, product.getStockQuantity());
        assertEquals(1, orderRepository.count());
    }
}