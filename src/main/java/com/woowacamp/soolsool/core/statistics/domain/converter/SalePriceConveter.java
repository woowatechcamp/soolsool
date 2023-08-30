package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.SalePrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SalePriceConveter implements AttributeConverter<SalePrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final SalePrice salePrice) {
        return salePrice.getPrice();
    }

    @Override
    public SalePrice convertToEntityAttribute(final BigInteger dbData) {
        return new SalePrice(dbData);
    }
}
