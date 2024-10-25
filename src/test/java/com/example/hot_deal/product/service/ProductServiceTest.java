package com.example.hot_deal.product.service;

import com.example.hot_deal.fixture.ProductFixture;
import com.example.hot_deal.product.domain.entity.Product;
import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.product.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanup() {
        productRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("제품 목록 조회 테스트")
    void getProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = IntStream.range(0, 10)
                .mapToObj(i -> ProductFixture.productFixture())
                .toList();
        productRepository.saveAllProducts(products);

        // When
        Page<ProductDTO> result = productService.getProducts(pageable);

        // Then
        assertEquals(products.size(), result.getContent().size());
    }
}