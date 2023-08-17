package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberMileageConverter implements AttributeConverter<MemberMileage, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final MemberMileage mileage) {
        return mileage.getMileage();
    }

    @Override
    public MemberMileage convertToEntityAttribute(final BigInteger dbData) {
        return null;
    }
}
