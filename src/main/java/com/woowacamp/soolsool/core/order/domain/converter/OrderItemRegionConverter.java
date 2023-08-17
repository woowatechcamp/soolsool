package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemRegion;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemRegionConverter implements AttributeConverter<OrderItemRegion, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemRegion region) {
        return region.getRegion();
    }

    @Override
    public OrderItemRegion convertToEntityAttribute(final String dbData) {
        return new OrderItemRegion(dbData);
    }
}
