package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorBrewType {

    SOJU("소주"),
    DISTILLED("증류주"),
    MAKGEOLLI("막걸리"),
    PURE("약주"),
    RICE("청주"),
    BERRY("과실주"),
    ETC("기타주류"),
    ;

    private final String type;
}
