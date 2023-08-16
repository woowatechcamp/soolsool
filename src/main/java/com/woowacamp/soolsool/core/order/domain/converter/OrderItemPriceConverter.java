package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.OrderItemPrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderItemPriceConverter implements AttributeConverter<OrderItemPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final OrderItemPrice price) {
        return price.getPrice();
    }

    @Override
    public OrderItemPrice convertToEntityAttribute(final BigInteger dbData) {
        return new OrderItemPrice(dbData);
    }
}
