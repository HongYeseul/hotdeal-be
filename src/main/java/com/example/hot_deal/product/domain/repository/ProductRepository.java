package com.example.hot_deal.product.domain.repository;

import com.example.hot_deal.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Product getProductById(Long id);

    Long getProductQuantityById(Long id);

    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    void deleteAll();
}
