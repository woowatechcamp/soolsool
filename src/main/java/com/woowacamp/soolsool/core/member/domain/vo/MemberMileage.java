package com.woowacamp.soolsool.core.member.domain.vo;

import java.math.BigInteger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MemberMileage {

    private final BigInteger mileage;

    public MemberMileage(final String mileage) {
        this(new BigInteger(mileage));
    }
}
