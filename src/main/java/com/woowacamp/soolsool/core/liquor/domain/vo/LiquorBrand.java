package com.woowacamp.soolsool.core.liquor.domain.vo;


import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class LiquorBrand {

    private static final int MAX_LENGTH = 30;

    private final String brand;

    public LiquorBrand(final String brand) {
        validateIsNotNullOrEmpty(brand);
        validateIsValidLength(brand);

        this.brand = brand;
    }

    private void validateIsValidLength(final String brand) {
        if (brand.length() > MAX_LENGTH) {
            throw new ShoppingException("술 브랜드는 20자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String brand) {
        if (!StringUtils.hasText(brand)) {
            throw new ShoppingException("술 브랜드는 null이거나 공백일 수 없습니다.");
        }
    }
}
