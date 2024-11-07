package com.example.hot_deal.payment.service;

import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.payment.repository.PaymentRepository;
import com.example.hot_deal.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    PaymentRepository paymentRepository;

    /**
     * Toss 결제 승인 요청
     * @param member
     * @param product
     */
    public void processPayment(Member member, Product product) {
        // TODO 결제 승인 요청
    }
}
