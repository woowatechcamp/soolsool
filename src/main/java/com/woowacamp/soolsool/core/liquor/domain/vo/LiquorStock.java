package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
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
            throw new SoolSoolException(LiquorErrorCode.INVALID_SIZE_STOCK);
        }
    }
}
