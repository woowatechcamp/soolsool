package com.woowacamp.soolsool.core.order.domain.converter;

import com.woowacamp.soolsool.core.order.domain.vo.OrerItemVolume;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderItemVolumeConverter implements AttributeConverter<OrerItemVolume, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final OrerItemVolume volume) {
        return volume.getVolume();
    }

    @Override
    public OrerItemVolume convertToEntityAttribute(final Integer dbData) {
        return new OrerItemVolume(dbData);
    }
}
