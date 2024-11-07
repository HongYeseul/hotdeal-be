package com.example.hot_deal.payment.repository;

import com.example.hot_deal.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends PaymentRepository, JpaRepository<Payment, Long> {
}
