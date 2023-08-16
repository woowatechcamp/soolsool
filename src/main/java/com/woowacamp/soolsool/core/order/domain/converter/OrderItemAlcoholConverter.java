package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.OrderItemAlcohol;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderItemAlcoholConverter implements AttributeConverter<OrderItemAlcohol, Double> {

    @Override
    public Double convertToDatabaseColumn(final OrderItemAlcohol alcohol) {
        return alcohol.getAlcohol();
    }

    @Override
    public OrderItemAlcohol convertToEntityAttribute(final Double dbData) {
        return new OrderItemAlcohol(dbData);
    }
}
