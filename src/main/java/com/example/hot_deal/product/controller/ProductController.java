package com.example.hot_deal.product.controller;

import com.example.hot_deal.common.config.web.ApiV1;
import com.example.hot_deal.common.domain.PageResponse;
import com.example.hot_deal.product.dto.ProductDTO;
import com.example.hot_deal.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiV1
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 판매 제품 목록 조회
     * 현재는 sorting 설정을 따로 해주지는 않았지만, 필요하다면 할 수 있습니다.
     */
    @GetMapping
    @Operation(summary = "제품 목록 조회", description = "사용자는 제품 목록을 조회할 수 있다. 기본은 10개씩이며 URL?page=0&size=2와 같이 쿼리로 사용할 수 있다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공적으로 제품 목록을 조회했습니다.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageResponse.class)
            )
    )
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProducts(pageable)
        );
    }
}
