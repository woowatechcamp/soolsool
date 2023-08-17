package com.woowacamp.soolsool.core.cart.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class CartItemQuantity {

    private final int quantity;
}
