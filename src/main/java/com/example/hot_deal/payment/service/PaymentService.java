package com.example.hot_deal.payment.service;

import com.example.hot_deal.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    PaymentRepository paymentRepository;
}
