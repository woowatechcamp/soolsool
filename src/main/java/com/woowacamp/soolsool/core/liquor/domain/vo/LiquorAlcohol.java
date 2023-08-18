package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class LiquorAlcohol {

    private final double alcohol;

    public LiquorAlcohol(final double alcohol) {
        validateIsGreaterThanZero(alcohol);

        this.alcohol = alcohol;
    }

    private void validateIsGreaterThanZero(final double alcohol) {
        if (alcohol < 0) {
            throw new ShoppingException("술 도수는 0 이상 실수여야 합니다.");
        }
    }
}
