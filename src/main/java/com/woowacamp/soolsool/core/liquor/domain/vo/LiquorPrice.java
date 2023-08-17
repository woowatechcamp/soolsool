package com.woowacamp.soolsool.core.liquor.domain.vo;


import java.math.BigInteger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class LiquorPrice {

    private final BigInteger price;
}
