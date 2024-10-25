package com.example.hot_deal.product.domain.repository;

import com.example.hot_deal.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    Product getProductById(Long id);

    Long getProductQuantityById(Long id);

    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    void deleteAll();

    List<Product> saveAllProducts(List<Product> products);
}
