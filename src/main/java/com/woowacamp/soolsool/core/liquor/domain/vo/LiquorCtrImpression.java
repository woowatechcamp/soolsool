package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class LiquorCtrImpression {

    private final Long impression;

    public LiquorCtrImpression(final Long impression) {
        validateIsNotNull(impression);
        validateIsNotLessThanZero(impression);

        this.impression = impression;
    }

    private void validateIsNotNull(final Long impression) {
        if (Objects.isNull(impression)) {
            throw new SoolSoolException(LiquorCtrErrorCode.NO_CONTENT_IMPRESSION);
        }
    }

    private void validateIsNotLessThanZero(final Long impression) {
        if (impression < 0) {
            throw new SoolSoolException(LiquorCtrErrorCode.INVALID_SIZE_IMPRESSION);
        }
    }
}
