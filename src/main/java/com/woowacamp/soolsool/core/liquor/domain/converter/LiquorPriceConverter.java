package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorPrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LiquorPriceConverter implements AttributeConverter<LiquorPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final LiquorPrice price) {
        return price.getPrice();
    }

    @Override
    public LiquorPrice convertToEntityAttribute(final BigInteger dbData) {
        return new LiquorPrice(dbData);
    }
}
