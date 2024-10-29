package com.example.hot_deal.product.dto;

import java.time.LocalDateTime;

import com.example.hot_deal.common.domain.Price;
import com.example.hot_deal.product.domain.entity.Product;
import lombok.Getter;

@Getter
public class ProductDTO {
    private final Long id;
    private final String name;
    private final Price price;
    private final Long quantity;
    private final LocalDateTime openTime;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getTotalPrice();
        this.quantity = product.getQuantity();
        this.openTime = product.getOpenTime();
    }
}
