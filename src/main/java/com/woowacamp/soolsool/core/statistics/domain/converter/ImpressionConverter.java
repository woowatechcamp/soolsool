package com.woowacamp.soolsool.core.statistics.domain.converter;

import com.woowacamp.soolsool.core.statistics.domain.vo.Impression;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ImpressionConverter implements AttributeConverter<Impression, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final Impression impression) {
        return impression.getCount();
    }

    @Override
    public Impression convertToEntityAttribute(final BigInteger dbData) {
        return new Impression(dbData);
    }
}
