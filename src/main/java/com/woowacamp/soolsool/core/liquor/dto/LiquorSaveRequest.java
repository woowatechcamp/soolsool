package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class LiquorSaveRequest {

    private final String brew;
    private final String region;
    private final String status;
    private final String name;
    private final String price;
    private final String brand;
    private final String imageUrl;
    private final Double alcohol;
    private final Integer volume;

    public Liquor toEntity(
        final LiquorBrew brew,
        final LiquorRegion region,
        final LiquorStatus status
    ) {

        return new Liquor(
            brew, region,
            status, name,
            price, brand, imageUrl,
            alcohol, volume
        );
    }
}
