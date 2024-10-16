package com.example.hot_deal.product.service;

import com.example.hot_deal.product.domain.repository.ProductRepository;
import com.example.hot_deal.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new).toList();
    }
}
