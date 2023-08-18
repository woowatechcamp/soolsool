package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LiquorStock {

    private final int stock;

    public LiquorStock(final int stock) {
        validateIsNotLessThanZero(stock);

        this.stock = stock;
    }

    private void validateIsNotLessThanZero(final int stock) {
        if (stock < 0) {
            throw new ShoppingException("술 재고는 0 미만일 수 없습니다.");
        }
    }
}
