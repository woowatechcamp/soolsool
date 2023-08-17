package com.woowacamp.soolsool.core.liquor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveLiquorRequest {

    private final String typeName;
    private final String regionName;
    private final String statusName;
    private final String name;
    private final String price;
    private final String brand;
    private final String imageUrl;
    private final Integer stock;
    private final Double alcohol;
    private final Integer volume;
}
