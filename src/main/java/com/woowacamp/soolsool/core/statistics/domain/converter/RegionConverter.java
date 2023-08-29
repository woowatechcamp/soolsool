package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.Region;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RegionConverter implements AttributeConverter<Region, String> {

    @Override
    public String convertToDatabaseColumn(final Region attribute) {
        return attribute.getName();
    }

    @Override
    public Region convertToEntityAttribute(final String dbData) {
        return new Region(dbData);
    }
}
