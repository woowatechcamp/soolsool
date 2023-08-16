package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.LiquorAlcohol;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LiquorAlcoholConverter implements AttributeConverter<LiquorAlcohol, Double> {

    @Override
    public Double convertToDatabaseColumn(final LiquorAlcohol alcohol) {
        return alcohol.getAlcohol();
    }

    @Override
    public LiquorAlcohol convertToEntityAttribute(final Double dbData) {
        return new LiquorAlcohol(dbData);
    }
}
