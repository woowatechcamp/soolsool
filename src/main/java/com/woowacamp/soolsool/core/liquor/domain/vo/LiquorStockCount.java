package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LiquorStockCount {

    private final int stock;

    public LiquorStockCount(final int stock) {
        validateIsNotLessThanZero(stock);

        this.stock = stock;
    }

    private void validateIsNotLessThanZero(final int stock) {
        if (stock < 0) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_SIZE_STOCK);
        }
    }

    public boolean isZero() {
        return this.stock == 0;
    }

    public LiquorStockCount decrease(final int stock) {
        return new LiquorStockCount(this.stock - stock);
    }

    public LiquorStockCount increase(final int stock) {
        return new LiquorStockCount(this.stock + stock);
    }
}
