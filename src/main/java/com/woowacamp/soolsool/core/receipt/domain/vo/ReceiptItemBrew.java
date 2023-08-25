package com.woowacamp.soolsool.core.receipt.domain.vo;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class ReceiptItemBrew {

    private static final int MAX_LENGTH = 20;

    private final String brew;

    public ReceiptItemBrew(final String brew) {
        validateIsNotNullOrEmpty(brew);
        validateIsValidLength(brew);

        this.brew = brew;
    }

    private void validateIsValidLength(final String brew) {
        if (brew.length() > MAX_LENGTH) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_LENGTH_NAME);
        }
    }

    private void validateIsNotNullOrEmpty(final String brew) {
        if (!StringUtils.hasText(brew)) {
            throw new SoolSoolException(LiquorErrorCode.NO_CONTENT_NAME);
        }
    }

}
