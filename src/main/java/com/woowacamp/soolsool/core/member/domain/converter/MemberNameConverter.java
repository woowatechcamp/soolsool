package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.vo.MemberName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberNameConverter implements AttributeConverter<MemberName, String> {

    @Override
    public String convertToDatabaseColumn(final MemberName name) {
        return name.getName();
    }

    @Override
    public MemberName convertToEntityAttribute(final String dbData) {
        return new MemberName(dbData);
    }
}
