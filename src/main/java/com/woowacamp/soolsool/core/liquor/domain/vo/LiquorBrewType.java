package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorBrewType {

    SOJU("소주", "SOJU"),
    DISTILLED("증류주", "DISTILLED"),
    MAKGEOLLI("막걸리", "MAKGEOLLI"),
    PURE("약주", "PURE"),
    RICE("청주", "RICE"),
    BERRY("과실주", "BERRY"),
    ETC("기타주류", "ETC"),
    ;

    private final String type;
    private final String eType;

}
