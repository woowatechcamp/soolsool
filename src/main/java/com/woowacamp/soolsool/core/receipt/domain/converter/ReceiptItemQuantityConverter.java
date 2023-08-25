package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemQuantity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemQuantityConverter
    implements AttributeConverter<ReceiptItemQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ReceiptItemQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public ReceiptItemQuantity convertToEntityAttribute(final Integer dbData) {
        return new ReceiptItemQuantity(dbData);
    }
}
