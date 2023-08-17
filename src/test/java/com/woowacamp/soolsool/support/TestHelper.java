package com.woowacamp.soolsool.support;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.domain.vo.MemberName;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPhoneNumber;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;

public class TestHelper {

    public static Member getMember() {
        return Member.builder()
            .role(MemberRole.builder()
                .name(MemberRoleType.CUSTOMER)
                .build())
            .email(new MemberEmail("test@email.com"))
            .password(new MemberPassword("test_password"))
            .name(new MemberName("최배달"))
            .phoneNumber(new MemberPhoneNumber("010-1234-5678"))
            .mileage(new MemberMileage("0"))
            .address(new MemberAddress("서울시 잠실역"))
            .build();
    }
}
