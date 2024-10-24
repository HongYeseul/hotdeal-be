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

    /**
     * 물건 구매
     * @param userId 구매 주체
     * @param productId 구매 대상
     */
    public void orderProduct(Long userId, Long productId) {
        String productKey = productId.toString();
        Product product = productRepository.getProductById(productId);
        productCountRepository.decrement(productKey, product.getStockQuantity().toString());

        purchasedUserRepository.purchase(userId, productId);
    }
}
