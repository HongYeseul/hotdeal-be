package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.repository.PurchasedUserRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductCountRepository;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final ProductCountRepository productCountRepository;
    private final PurchasedUserRepository purchasedUserRepository;

    public void orderProduct(Long userId, Long productId) {
        String productKey = productId.toString();
        Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
        productCountRepository.decrement(productKey, product.getStockQuantity().toString());

        purchasedUserRepository.purchase(userId, productId);
    }
}
