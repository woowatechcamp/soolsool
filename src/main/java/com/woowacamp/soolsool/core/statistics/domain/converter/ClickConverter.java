package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.Click;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ClickConverter implements AttributeConverter<Click, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final Click click) {
        return click.getCount();
    }

    @Override
    public Click convertToEntityAttribute(final BigInteger dbData) {
        return new Click(dbData);
    }
}
