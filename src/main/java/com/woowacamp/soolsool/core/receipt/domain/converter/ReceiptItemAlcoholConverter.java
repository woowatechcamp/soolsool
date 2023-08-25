package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItemAlcohol;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemAlcoholConverter implements AttributeConverter<ReceiptItemAlcohol, Double> {

    @Override
    public Double convertToDatabaseColumn(final ReceiptItemAlcohol alcohol) {
        return alcohol.getAlcohol();
    }

    @Override
    public ReceiptItemAlcohol convertToEntityAttribute(final Double dbData) {
        return new ReceiptItemAlcohol(dbData);
    }
}
