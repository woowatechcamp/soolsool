package com.woowacamp.soolsool.core.receipt.domain.converter;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItemVolume;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReceiptItemVolumeConverter implements AttributeConverter<ReceiptItemVolume, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ReceiptItemVolume volume) {
        return volume.getVolume();
    }

    @Override
    public ReceiptItemVolume convertToEntityAttribute(final Integer dbData) {
        return new ReceiptItemVolume(dbData);
    }
}
