package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.BrewType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BrewTypeConverter implements AttributeConverter<BrewType, String> {

    @Override
    public String convertToDatabaseColumn(final BrewType brewType) {
        return brewType.getType();
    }

    @Override
    public BrewType convertToEntityAttribute(final String dbData) {
        return new BrewType(dbData);
    }
}
