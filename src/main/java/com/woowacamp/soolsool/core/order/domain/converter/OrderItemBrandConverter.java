package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemBrand;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemBrandConverter implements AttributeConverter<OrderItemBrand, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemBrand brand) {
        return brand.getBrand();
    }

    @Override
    public OrderItemBrand convertToEntityAttribute(final String dbData) {
        return new OrderItemBrand(dbData);
    }
}
