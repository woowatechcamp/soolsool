package com.woowacamp.soolsool.core.receipt.converter;

import com.woowacamp.soolsool.core.receipt.vo.ReceiptQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptQuantityConverter implements AttributeConverter<ReceiptQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ReceiptQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public ReceiptQuantity convertToEntityAttribute(final Integer dbData) {
        return new ReceiptQuantity(dbData);
    }
}
