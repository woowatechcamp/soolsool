package com.woowacamp.soolsool.core.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MemberPhoneNumber {

    private final String phoneNumber;
}
