package com.woowacamp.soolsool.core.liquor.dto.response;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorDetailResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String brand;
    private final String imageUrl;
    private final Integer stock;
    private final Double alcohol;
    private final Integer volume;
    private final List<LiquorElementResponse> relatedLiquors; // TODO: 연관 상품 API 분리

    public static LiquorDetailResponse of(final Liquor liquor, final List<Liquor> relatedLiquors) {
        final List<LiquorElementResponse> relatedLiquorResponses = relatedLiquors.stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());

        return new LiquorDetailResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getBrand(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            liquor.getAlcohol(),
            liquor.getVolume(),
            relatedLiquorResponses
        );
    }
}
