package com.woowacamp.soolsool.core.cart.domain.converter;

import com.woowacamp.soolsool.core.cart.domain.vo.CartItemQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CartItemQuantityConverter implements AttributeConverter<CartItemQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final CartItemQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public CartItemQuantity convertToEntityAttribute(final Integer dbData) {
        return new CartItemQuantity(dbData);
    }
}
