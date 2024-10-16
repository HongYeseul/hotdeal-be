package com.example.hot_deal.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "커스텀된 페이지 응답 DTO")
public record PageResponse<E>(
        boolean hasNext,
        List<E> items,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean isLast
) {
    public static <E> PageResponse<E> from(final Page<E> page) {
        return new PageResponse<>(
                page.hasNext(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast()
        );
    }
}
