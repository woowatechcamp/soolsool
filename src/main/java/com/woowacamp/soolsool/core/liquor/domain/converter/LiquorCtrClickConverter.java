package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import javax.persistence.AttributeConverter;

public class LiquorCtrClickConverter implements AttributeConverter<LiquorCtrClick, Long> {

    @Override
    public Long convertToDatabaseColumn(final LiquorCtrClick click) {
        return click.getCount();
    }

    @Override
    public LiquorCtrClick convertToEntityAttribute(final Long dbData) {
        return new LiquorCtrClick(dbData);
    }
}
