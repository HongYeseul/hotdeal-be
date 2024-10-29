package com.example.hot_deal.common.domain;

import com.example.hot_deal.common.exception.HotDealException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.example.hot_deal.common.exception.code.CommonErrorCode.INVALID_PRICE;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

    @Column(nullable = false)
    private BigDecimal price;

    public Price(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new HotDealException(INVALID_PRICE);
        }
        this.price = price;
    }
}
