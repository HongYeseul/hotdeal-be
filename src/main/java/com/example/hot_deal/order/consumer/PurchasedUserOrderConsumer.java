package com.example.hot_deal.order.consumer;

import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class PurchasedUserOrderConsumer {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    ProductRepository productRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @KafkaListener(topics = "applied-users", groupId = "order-group")
    public void processOrder(String message) {
        String[] parts = message.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 메시지 형식입니다.");
        }

        Long userId = Long.parseLong(parts[0]);
        Long productId = Long.parseLong(parts[1]);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        product.decreaseQuantity();
        productRepository.save(product);

        orderRepository.save(Order.builder()
                .user(user)
                .product(product)
                .build());

        log.info("사용자 {}의 주문이 DB에 저장되었습니다.", userId);
    }
}
