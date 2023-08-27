package com.woowacamp.soolsool.core.liquor.domain.vo;

import java.math.BigInteger;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class LiquorInfo {

    private final LiquorName name;
    private final LiquorPrice price;
    private final LiquorBrand brand;
    private final LiquorImageUrl imageUrl;
    private final LiquorAlcohol alcohol;
    private final LiquorVolume volume;

    @Builder
    public LiquorInfo(
        @NonNull final String name,
        @NonNull final BigInteger price,
        @NonNull final String brand,
        @NonNull final String imageUrl,
        @NonNull final Double alcohol,
        @NonNull final Integer volume
    ) {
        this.name = new LiquorName(name);
        this.price = new LiquorPrice(price);
        this.brand = new LiquorBrand(brand);
        this.imageUrl = new LiquorImageUrl(imageUrl);
        this.alcohol = new LiquorAlcohol(alcohol);
        this.volume = new LiquorVolume(volume);
    }
}
