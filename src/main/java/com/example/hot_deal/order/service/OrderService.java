package com.example.hot_deal.order.service;

import com.example.hot_deal.order.domain.repository.PurchasedUserRepository;
import com.example.hot_deal.product.domain.repository.redis.ProductCountRepository;
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
     * 1. 물건 구매 요청이 들어오면 레디스에서 물건 수량을 감소
     * 2. 물건 요청을 카프카에 기록 해뒀다가 카프카의 Consumer 가 메시지를 받아 순차적으로 재고를 감소(DB 반영)
     * @param userId 구매 주체
     * @param productId 구매 대상
     */
    public void orderProduct(Long userId, Long productId) {
        String productKey = productId.toString();
        productCountRepository.decrement(
                productKey,
                productRepository.getProductQuantityById(productId).toString()
        );

        purchasedUserRepository.purchase(userId, productId);
    }
}
