package com.woowacamp.soolsool.core.cart.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemSaveRequest {

    private final Long liquorId;
    private final Integer quantity;
}
