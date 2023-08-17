package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class LiquorImageUrl {

    private static final int MAX_LENGTH = 255;

    private final String imageUrl;

    public LiquorImageUrl(final String imageUrl) {
        validateIsNotNullOrEmpty(imageUrl);
        validateIsValidLength(imageUrl);

        this.imageUrl = imageUrl;
    }

    private void validateIsValidLength(final String imageUrl) {
        if (imageUrl.length() > MAX_LENGTH) {
            throw new ShoppingException("술 이미지 경로는 255자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            throw new ShoppingException("술 이미지 경로는 null이거나 공백일 수 없습니다.");
        }
    }
}
