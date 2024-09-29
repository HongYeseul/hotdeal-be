package com.example.hot_deal.order.domain.repository;

import com.example.hot_deal.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
