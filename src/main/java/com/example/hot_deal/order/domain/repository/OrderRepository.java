package com.example.hot_deal.order.domain.repository;

import com.example.hot_deal.order.domain.entity.Order;

public interface OrderRepository {

    Order save(Order order);
}
