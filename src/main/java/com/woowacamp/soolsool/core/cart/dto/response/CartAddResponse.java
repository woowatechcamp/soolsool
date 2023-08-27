package com.woowacamp.soolsool.core.cart.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CartAddResponse {

    private final Long cartItemId;

    @JsonCreator
    public CartAddResponse(final Long cartItemId) {
        this.cartItemId = cartItemId;
    }
}
