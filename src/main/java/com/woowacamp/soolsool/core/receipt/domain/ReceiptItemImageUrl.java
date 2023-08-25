package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class ReceiptItemImageUrl {

    private static final int MAX_LENGTH = 255;

    private final String imageUrl;

    public ReceiptItemImageUrl(final String imageUrl) {
        validateIsNotNullOrEmpty(imageUrl);
        validateIsValidLength(imageUrl);

        this.imageUrl = imageUrl;
    }

    private void validateIsValidLength(final String imageUrl) {
        if (imageUrl.length() > MAX_LENGTH) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_LENGTH_IMAGE_URL);
        }
    }

    private void validateIsNotNullOrEmpty(final String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            throw new SoolSoolException(LiquorErrorCode.NO_CONTENT_IMAGE_URL);
        }
    }
}
