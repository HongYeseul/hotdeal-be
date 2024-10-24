package com.example.hot_deal.order.consumer;

import com.example.hot_deal.common.exception.HotDealException;
import lombok.Getter;

import static com.example.hot_deal.common.exception.code.CommonErrorCode.INVALID_NUMBER_FORMAT;
import static com.example.hot_deal.order.constants.error.OrderErrorCode.INVALID_MESSAGE_FORMAT;

@Getter
public class PurchasedRequest {

    private static final String DELIMITER = ":";

    private final Long memberId;
    private final Long productId;

    private PurchasedRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public static PurchasedRequest from(String message) {
        String[] parts = splitByDelimiter(message);

        return new PurchasedRequest(
                convertToLong(parts[0]),
                convertToLong(parts[1])
        );
    }

    private static String[] splitByDelimiter(String message) {
        String[] parts = message.split(DELIMITER);
        if (parts.length != 2) {
            throw new HotDealException(INVALID_MESSAGE_FORMAT);
        }
        return parts;
    }

    private static Long convertToLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            throw new HotDealException(INVALID_NUMBER_FORMAT);
        }
    }
}
