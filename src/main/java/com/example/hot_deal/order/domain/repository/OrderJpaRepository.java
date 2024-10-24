package com.example.hot_deal.order.domain.repository;

import com.example.hot_deal.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends OrderRepository, JpaRepository<Order, Long> {
}
