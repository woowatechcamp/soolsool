package com.woowacamp.soolsool.core.member.domain.converter;

import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberAddressConverter implements AttributeConverter<MemberAddress, String> {

    @Override
    public String convertToDatabaseColumn(final MemberAddress address) {
        return address.getAddress();
    }

    @Override
    public MemberAddress convertToEntityAttribute(final String dbData) {
        return new MemberAddress(dbData);
    }
}
