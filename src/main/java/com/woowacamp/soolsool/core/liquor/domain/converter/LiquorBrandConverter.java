package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrand;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LiquorBrandConverter implements AttributeConverter<LiquorBrand, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorBrand brand) {
        return brand.getBrand();
    }

    @Override
    public LiquorBrand convertToEntityAttribute(final String dbData) {
        return new LiquorBrand(dbData);
    }
}
