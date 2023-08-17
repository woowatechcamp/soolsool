package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberEmailConverter implements AttributeConverter<MemberEmail, String> {

    @Override
    public String convertToDatabaseColumn(final MemberEmail email) {
        return email.getEmail();
    }

    @Override
    public MemberEmail convertToEntityAttribute(final String dbData) {
        return new MemberEmail(dbData);
    }
}
