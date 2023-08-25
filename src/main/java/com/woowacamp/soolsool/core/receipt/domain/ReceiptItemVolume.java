package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReceiptItemVolume {

    private final int volume;

    public ReceiptItemVolume(final int volume) {
        validateIsNotLessThanZero(volume);

        this.volume = volume;
    }

    private void validateIsNotLessThanZero(final int volume) {
        if (volume < 0) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_SIZE_VOLUME);
        }
    }
}
