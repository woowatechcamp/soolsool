package com.woowacamp.soolsool.core.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CartItemModifyRequest {

    public final Integer liquorQuantity;

    @JsonCreator
    public CartItemModifyRequest(@JsonProperty final Integer liquorQuantity) {
        this.liquorQuantity = liquorQuantity;
    }
}
