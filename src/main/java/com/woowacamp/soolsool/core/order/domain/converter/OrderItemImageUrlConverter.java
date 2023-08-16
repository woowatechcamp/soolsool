package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.OrderItemImageUrl;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderItemImageUrlConverter implements AttributeConverter<OrderItemImageUrl, String> {

    @Override
    public String convertToDatabaseColumn(final OrderItemImageUrl imageUrl) {
        return imageUrl.getImageUrl();
    }

    @Override
    public OrderItemImageUrl convertToEntityAttribute(final String dbData) {
        return new OrderItemImageUrl(dbData);
    }
}
