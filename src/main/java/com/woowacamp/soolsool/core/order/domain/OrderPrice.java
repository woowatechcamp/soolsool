package com.woowacamp.soolsool.core.order.domain;

import java.math.BigInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderPrice {

    private final BigInteger price;
}
