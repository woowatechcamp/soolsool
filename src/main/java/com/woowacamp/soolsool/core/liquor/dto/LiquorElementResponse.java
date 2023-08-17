package com.woowacamp.soolsool.core.liquor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorElementResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;
}
