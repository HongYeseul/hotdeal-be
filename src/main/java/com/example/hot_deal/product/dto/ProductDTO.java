package com.example.hot_deal.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.hot_deal.product.domain.entity.Product;
import lombok.Getter;

@Getter
public class ProductDTO {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Long quantity;
    private final LocalDateTime openTime;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getStockQuantity();
        this.openTime = product.getOpenTime();
    }
}
