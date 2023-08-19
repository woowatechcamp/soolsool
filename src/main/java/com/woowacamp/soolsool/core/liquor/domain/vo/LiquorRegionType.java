package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorRegionType {

    GYEONGGI_DO("경기도"),
    GANGWON_DO("강원도"),
    CHUNGCHEONGBUK_DO("충청북도"),
    CHUNGCHEONGNAM_DO("충청남도"),
    GYEONGSANGBUK_DO("경상북도"),
    GYEONGSANGNAM_DO("경상남도"),
    JEOLLABUK_DO("전라북도"),
    JEOLLANAM_DO("전라남도"),
    JEJU_DO("제주도"),
    ;

    private final String name;
}
