package com.example.hot_deal.product.domain.repository;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.hot_deal.product.constants.error.ProductErrorCode.PRODUCT_NOT_FOUND;

public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, Long> {

    default Product getProductById(Long id) {
        return findById(id).orElseThrow(
                () -> new HotDealException(PRODUCT_NOT_FOUND)
        );
    }

    Page<Product> findAll(Pageable pageable);
}