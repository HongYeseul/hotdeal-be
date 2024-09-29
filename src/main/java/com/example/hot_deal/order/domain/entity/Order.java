package com.example.hot_deal.order.domain.entity;

import com.example.hot_deal.common.domain.BaseTimeEntity;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
