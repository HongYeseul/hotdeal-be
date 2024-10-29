package com.example.hot_deal.product.domain.entity;


import com.example.hot_deal.common.domain.BaseTimeEntity;
import com.example.hot_deal.common.domain.Price;
import com.example.hot_deal.common.domain.Quantity;
import com.example.hot_deal.common.exception.HotDealException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.hot_deal.product.constants.error.ProductErrorCode.INSUFFICIENT_PRODUCT_QUANTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Price totalPrice;

    @Embedded
    private Quantity quantity;

    @Column(nullable = false)
    private LocalDateTime openTime;

    public Product(String name, Price price, Quantity quantity, LocalDateTime openTime) {
        this(null, name, price, quantity, openTime);
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    public void decreaseQuantity() {
        if (!this.quantity.decrease()) {
            throw new HotDealException(INSUFFICIENT_PRODUCT_QUANTITY);
        }
    }
}
