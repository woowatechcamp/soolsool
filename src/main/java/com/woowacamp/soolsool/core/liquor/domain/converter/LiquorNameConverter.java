package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.LiquorName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LiquorNameConverter implements AttributeConverter<LiquorName, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorName name) {
        return name.getName();
    }

    @Override
    public LiquorName convertToEntityAttribute(final String dbData) {
        return new LiquorName(dbData);
    }
}
