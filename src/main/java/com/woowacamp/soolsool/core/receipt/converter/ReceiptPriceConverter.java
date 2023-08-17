package com.woowacamp.soolsool.core.receipt.converter;

import com.woowacamp.soolsool.core.receipt.vo.ReceiptPrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptPriceConverter implements AttributeConverter<ReceiptPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final ReceiptPrice price) {
        return price.getPrice();
    }

    @Override
    public ReceiptPrice convertToEntityAttribute(final BigInteger dbData) {
        return new ReceiptPrice(dbData);
    }
}
