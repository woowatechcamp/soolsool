package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class LiquorCtrClick {

    private final Long click;

    public LiquorCtrClick(final Long Click) {
        validateIsNotNull(Click);
        validateIsNotLessThanZero(Click);

        this.click = Click;
    }

    private void validateIsNotNull(final Long Click) {
        if (Objects.isNull(Click)) {
            throw new SoolSoolException(LiquorCtrErrorCode.NO_CONTENT_CLICK);
        }
    }

    private void validateIsNotLessThanZero(final Long Click) {
        if (Click < 0) {
            throw new SoolSoolException(LiquorCtrErrorCode.INVALID_SIZE_CLICK);
        }
    }

    public LiquorCtrClick increaseOne() {
        return new LiquorCtrClick(this.click + 1);
    }
}
