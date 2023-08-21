package com.woowacamp.soolsool.core.order.domain.vo;


import com.woowacamp.soolsool.core.order.code.OrderErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class OrderItemBrand {

    private static final int MAX_LENGTH = 20;

    private final String brand;

    public OrderItemBrand(final String brand) {
        validateIsNotNullOrEmpty(brand);
        validateIsValidLength(brand);

        this.brand = brand;
    }

    private void validateIsValidLength(final String brand) {
        if (brand.length() > MAX_LENGTH) {
            throw new SoolSoolException(OrderErrorCode.INVALID_LENGTH_BRAND);
        }
    }

    private void validateIsNotNullOrEmpty(final String brand) {
        if (!StringUtils.hasText(brand)) {
            throw new SoolSoolException(OrderErrorCode.NO_CONTENT_BRAND);
        }
    }
}
