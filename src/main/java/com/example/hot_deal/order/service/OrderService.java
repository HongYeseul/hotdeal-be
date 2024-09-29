package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void orderProduct(Long userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Product product = productRepository.findById(orderId).orElseThrow(RuntimeException::new);

        // product 개수 낮추기
        if (!product.decreaseQuantity()) {
            throw new IllegalArgumentException("재고는 0개 미만이 될 수 없습니다.");
        }

        orderRepository.save(Order.builder()
                .user(user)
                .product(product)
                .build());
    }
}
