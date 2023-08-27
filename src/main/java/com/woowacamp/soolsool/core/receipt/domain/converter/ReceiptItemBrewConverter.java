package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemBrew;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemBrewConverter implements AttributeConverter<ReceiptItemBrew, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemBrew brand) {
        return brand.getBrew();
    }

    @Override
    public ReceiptItemBrew convertToEntityAttribute(final String dbData) {
        return new ReceiptItemBrew(dbData);
    }
}


