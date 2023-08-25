package com.woowacamp.soolsool.core.receipt.domain.vo;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.INVALID_SIZE_ALCOHOL;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ReceiptItemAlcohol {

    private final double alcohol;

    public ReceiptItemAlcohol(final double alcohol) {
        validateIsGreaterThanZero(alcohol);

        this.alcohol = alcohol;
    }

    private void validateIsGreaterThanZero(final double alcohol) {
        if (alcohol < 0) {
            throw new SoolSoolException(INVALID_SIZE_ALCOHOL);
        }
    }
}
