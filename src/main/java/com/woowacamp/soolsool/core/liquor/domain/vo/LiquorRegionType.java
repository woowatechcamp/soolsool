package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorRegionType {

    GYEONGGI_DO("경기도", "GYEONGGI_DO"),
    GANGWON_DO("강원도", "GANGWON_DO"),
    CHUNGCHEONGBUK_DO("충청북도", "CHUNGCHEONGBUK_DO"),
    CHUNGCHEONGNAM_DO("충청남도", "CHUNGCHEONGNAM_DO"),
    GYEONGSANGBUK_DO("경상북도", "GYEONGSANGBUK_DO"),
    GYEONGSANGNAM_DO("경상남도", "GYEONGSANGNAM_DO"),
    JEOLLABUK_DO("전라북도", "JEOLLABUK_DO"),
    JEOLLANAM_DO("전라남도", "JEOLLANAM_DO"),
    JEJU_DO("제주도", "JEJU_DO"),
    ;

    private final String name;
    private final String eName;

}
