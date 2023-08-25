package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemNameConverter implements AttributeConverter<ReceiptItemName, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemName name) {
        return name.getName();
    }

    @Override
    public ReceiptItemName convertToEntityAttribute(final String dbData) {
        return new ReceiptItemName(dbData);
    }
}

