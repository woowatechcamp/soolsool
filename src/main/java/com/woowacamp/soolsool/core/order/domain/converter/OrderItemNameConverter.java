package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemNameConverter implements AttributeConverter<OrderItemName, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemName name) {
        return name.getName();
    }

    @Override
    public OrderItemName convertToEntityAttribute(final String dbData) {
        return new OrderItemName(dbData);
    }
}
