package com.woowacamp.soolsool.core.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CartItemModifyRequest {

    @NotBlank(message = "수량은 필수 입력 값입니다.")
    @Positive(message = "수량은 양수만 요청할 수 있습니다.")
    public final Integer liquorQuantity;

    @JsonCreator
    public CartItemModifyRequest(@JsonProperty final Integer liquorQuantity) {
        this.liquorQuantity = liquorQuantity;
    }
}
