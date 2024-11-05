package com.example.hot_deal.order.service;

import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.service.MemberService;
import com.example.hot_deal.order.consumer.PurchasedRequest;
import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.order.domain.repository.PurchasedUserRepository;
import com.example.hot_deal.product.domain.entity.Product;
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

    private final OrderRepository orderRepository;
    private final MemberService memberService;

    /**
     * 물건 구매 요청
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

    /**
     * 물건 구매 로직
     * @param request 카프카에서 가지고 온 구매 요청 데이터
     */
    public void processOrderRequest(PurchasedRequest request) {
        Member member = memberService.getMemberById(request.getMemberId());
        Product product = productRepository.getProductById(request.getProductId());

        product.decreaseQuantity();
        productRepository.save(product);

        orderRepository.save(new Order(member, product.getName()));
    }
}
