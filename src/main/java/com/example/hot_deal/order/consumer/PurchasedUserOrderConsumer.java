package com.example.hot_deal.order.consumer;

import com.example.hot_deal.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hot_deal.common.constants.KafkaConstants.PRODUCT_ORDER_GROUP_ID;
import static com.example.hot_deal.common.constants.KafkaConstants.PRODUCT_ORDER_TOPIC;


@Slf4j
@Component
@AllArgsConstructor
public class PurchasedUserOrderConsumer {

    private final OrderService orderService;

    /**
     * KafkaConsumer: 제품 구매
     * orderService.processOrderRequest 메서드 요청
     * @param message 카프카 메시지(memberId:productId)
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @KafkaListener(topics = PRODUCT_ORDER_TOPIC, groupId = PRODUCT_ORDER_GROUP_ID)
    public void processOrder(String message) {
        PurchasedRequest request = PurchasedRequest.from(message);
        orderService.processOrderRequest(request);
        log.info("사용자 {}의 주문이 DB에 저장되었습니다.", request.getMemberId());
    }
}
