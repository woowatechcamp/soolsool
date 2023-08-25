package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemRegion;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemRegionConverter implements AttributeConverter<ReceiptItemRegion, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemRegion brand) {
        return brand.getRegion();
    }

    @Override
    public ReceiptItemRegion convertToEntityAttribute(final String dbData) {
        return new ReceiptItemRegion(dbData);
    }
}
