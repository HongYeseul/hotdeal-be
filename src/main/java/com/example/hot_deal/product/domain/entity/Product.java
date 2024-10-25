package com.example.hot_deal.product.domain.entity;


import com.example.hot_deal.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long stockQuantity;

    @Column(nullable = false)
    private LocalDateTime openTime;

    public Product(String name, BigDecimal price, Long stockQuantity, LocalDateTime openTime) {
        this(null, name, price, stockQuantity, openTime);
    }

    public boolean decreaseQuantity() {
        if (this.stockQuantity <= 0) {
            return false;
        }
        this.stockQuantity--;
        return true;
    }
}
