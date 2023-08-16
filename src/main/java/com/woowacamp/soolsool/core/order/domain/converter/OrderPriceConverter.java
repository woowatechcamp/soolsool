package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.OrderPrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderPriceConverter implements AttributeConverter<OrderPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final OrderPrice price) {
        return price.getPrice();
    }

    @Override
    public OrderPrice convertToEntityAttribute(final BigInteger dbData) {
        return new OrderPrice(dbData);
    }
}
