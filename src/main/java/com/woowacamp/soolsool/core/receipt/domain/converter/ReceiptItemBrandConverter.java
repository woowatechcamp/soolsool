package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemBrand;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemBrandConverter implements AttributeConverter<ReceiptItemBrand, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemBrand brand) {
        return brand.getBrand();
    }

    @Override
    public ReceiptItemBrand convertToEntityAttribute(final String dbData) {
        return new ReceiptItemBrand(dbData);
    }
}

