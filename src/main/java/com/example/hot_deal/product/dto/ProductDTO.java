package com.example.hot_deal.product.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.hot_deal.product.domain.entity.Product;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private LocalDateTime openTime;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getStockQuantity();
        this.openTime = product.getOpenTime();
    }
}
