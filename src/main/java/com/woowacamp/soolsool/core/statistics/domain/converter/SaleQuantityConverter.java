package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.SaleQuantity;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SaleQuantityConverter implements AttributeConverter<SaleQuantity, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final SaleQuantity saleQuantity) {
        return saleQuantity.getQuantity();
    }

    @Override
    public SaleQuantity convertToEntityAttribute(final BigInteger dbData) {
        return new SaleQuantity(dbData);
    }
}
