package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.MemberPhoneNumber;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberPhoneNumberConverter implements AttributeConverter<MemberPhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(final MemberPhoneNumber phoneNumber) {
        return phoneNumber.getPhoneNumber();
    }

    @Override
    public MemberPhoneNumber convertToEntityAttribute(final String dbData) {
        return new MemberPhoneNumber(dbData);
    }
}
