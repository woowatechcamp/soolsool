package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemQuantityConverter implements AttributeConverter<OrderItemQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final OrderItemQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public OrderItemQuantity convertToEntityAttribute(final Integer dbData) {
        return new OrderItemQuantity(dbData);
    }
}
