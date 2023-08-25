package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemImageUrl;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemImageUrlConverter implements
    AttributeConverter<ReceiptItemImageUrl, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemImageUrl imageUrl) {
        return imageUrl.getImageUrl();
    }

    @Override
    public ReceiptItemImageUrl convertToEntityAttribute(final String dbData) {
        return new ReceiptItemImageUrl(dbData);
    }
}

