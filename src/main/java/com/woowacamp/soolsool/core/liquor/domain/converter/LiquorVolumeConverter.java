package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.LiquorVolume;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LiquorVolumeConverter implements AttributeConverter<LiquorVolume, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final LiquorVolume volume) {
        return volume.getVolume();
    }

    @Override
    public LiquorVolume convertToEntityAttribute(final Integer dbData) {
        return new LiquorVolume(dbData);
    }
}
