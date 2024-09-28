package com.example.hot_deal.product.domain.repository;

import com.example.hot_deal.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
