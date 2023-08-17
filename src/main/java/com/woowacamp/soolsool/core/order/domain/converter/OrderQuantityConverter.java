package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderQuantityConverter implements AttributeConverter<OrderQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final OrderQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public OrderQuantity convertToEntityAttribute(final Integer dbData) {
        return new OrderQuantity(dbData);
    }
}
