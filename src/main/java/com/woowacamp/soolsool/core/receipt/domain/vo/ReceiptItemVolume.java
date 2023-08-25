package com.woowacamp.soolsool.core.receipt.domain.vo;

import com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode;
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
            throw new SoolSoolException(ReceiptErrorCode.INVALID_SIZE_VOLUME);
        }
    }
}
