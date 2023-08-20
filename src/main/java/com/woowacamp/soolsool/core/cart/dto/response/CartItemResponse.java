package com.woowacamp.soolsool.core.cart.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemResponse {

    private final Long id;
    private final String liquorName;
    private final String liquorPrice;
    private final String imageUrl;
    private final Integer liquorQuantity;

    @JsonCreator
    public static CartItemResponse from(@JsonProperty final CartItem cartItem) {
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getLiquor().getName(),
            cartItem.getLiquor().getPrice().toString(),
            cartItem.getLiquor().getImageUrl(),
            cartItem.getQuantity()
        );
    }

}
