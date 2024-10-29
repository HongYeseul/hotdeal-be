package com.example.hot_deal.order.consumer;

import com.example.hot_deal.order.domain.entity.Order;
import com.example.hot_deal.order.domain.repository.OrderRepository;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.product.domain.repository.ProductRepository;
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
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @KafkaListener(topics = "applied-users", groupId = "order-group")
    public void processOrder(String message) {
        PurchasedRequest request = PurchasedRequest.from(message);

        Member member = memberRepository.getMemberById(request.getMemberId());
        Product product = productRepository.getProductById(request.getProductId());

        product.decreaseQuantity();
        productRepository.save(product);

        orderRepository.save(new Order(member, product.getName()));

        log.info("사용자 {}의 주문이 DB에 저장되었습니다.", request.getMemberId());
    }
}
