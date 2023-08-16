package com.woowacamp.soolsool.core.cart.domain.converter;

import com.woowacamp.soolsool.core.cart.domain.CartItemQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
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
