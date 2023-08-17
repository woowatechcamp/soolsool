package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemTypeConverter implements AttributeConverter<OrderItemType, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemType type) {
        return type.getType();
    }

    @Override
    public OrderItemType convertToEntityAttribute(final String dbData) {
        return new OrderItemType(dbData);
    }
}
