package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemPrice;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemPriceConverter implements AttributeConverter<ReceiptItemPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final ReceiptItemPrice price) {
        return price.getPrice();
    }

    @Override
    public ReceiptItemPrice convertToEntityAttribute(final BigInteger dbData) {
        return new ReceiptItemPrice(dbData);
    }
}

