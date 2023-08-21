package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrderItemBrand.OrderMileageUsage;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderMileageUsageConverter implements
    AttributeConverter<OrderMileageUsage, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final OrderMileageUsage mileage) {
        return mileage.getMileage();
    }

    @Override
    public OrderMileageUsage convertToEntityAttribute(final BigInteger dbData) {
        return new OrderMileageUsage(dbData);
    }
}
