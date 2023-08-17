package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorImageUrl;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LiquorImageUrlConverter implements AttributeConverter<LiquorImageUrl, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorImageUrl imageUrl) {
        return imageUrl.getImageUrl();
    }

    @Override
    public LiquorImageUrl convertToEntityAttribute(final String dbData) {
        return new LiquorImageUrl(dbData);
    }
}
