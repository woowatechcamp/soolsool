package com.woowacamp.soolsool.core.member.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {

    CUSTOMER("구매자"),
    VENDOR("판매자"),
    ;

    private final String type;
}
