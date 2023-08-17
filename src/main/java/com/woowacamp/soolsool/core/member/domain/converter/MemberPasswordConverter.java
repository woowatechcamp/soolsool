package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberPasswordConverter implements AttributeConverter<MemberPassword, String> {

    @Override
    public String convertToDatabaseColumn(final MemberPassword password) {
        return password.getPassword();
    }

    @Override
    public MemberPassword convertToEntityAttribute(final String dbData) {
        return null;
    }
}
