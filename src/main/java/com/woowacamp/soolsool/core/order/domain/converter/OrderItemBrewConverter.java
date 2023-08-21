package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemBrew;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemBrewConverter implements AttributeConverter<OrderItemBrew, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemBrew type) {
        return type.getType();
    }

    @Override
    public OrderItemBrew convertToEntityAttribute(final String dbData) {
        return new OrderItemBrew(dbData);
    }
}
