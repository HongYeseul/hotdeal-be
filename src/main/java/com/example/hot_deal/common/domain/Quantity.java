package com.example.hot_deal.common.domain;

import com.example.hot_deal.common.exception.HotDealException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.hot_deal.common.exception.code.CommonErrorCode.INVALID_QUANTITY;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quantity {

    private Long quantity;

    public Quantity(Long quantity) {
        if (quantity < 0) {
            throw new HotDealException(INVALID_QUANTITY);
        }
        this.quantity = quantity;
    }

    public boolean decrease() {
        if (this.quantity <= 0) {
            return false;
        }
        this.quantity--;
        return true;
    }
}
