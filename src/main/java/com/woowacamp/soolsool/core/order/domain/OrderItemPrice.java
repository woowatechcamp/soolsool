package com.woowacamp.soolsool.core.order.domain;

import java.math.BigInteger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class OrderItemPrice {

    private final BigInteger price;
}
