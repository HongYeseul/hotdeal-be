package com.example.hot_deal.product.controller;

import com.example.hot_deal.product.dto.ProductDTO;
import com.example.hot_deal.product.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProducts(pageable)
        );
    }
}
