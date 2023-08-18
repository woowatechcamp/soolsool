package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LiquorVolume {

    private final int volume;

    public LiquorVolume(final int volume) {
        validateIsNotLessThanZero(volume);

        this.volume = volume;
    }

    private void validateIsNotLessThanZero(final int volume) {
        if (volume < 0) {
            throw new ShoppingException("술 재고는 0 미만일 수 없습니다.");
        }
    }
}
