package com.woowacamp.soolsool.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPhoneNumber;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("멩버 : 도메인 테스트")
class MemberTest {

    @Test
    @DisplayName("성공 : Member Entity 객체 생성 성공")
    void createMemberEntity() {
        // given
        String email = "test@email.com";
        String password = "test_password";
        String phoneNumber = "010-1234-5678";
        String address = "서울시 잠실 우아한 형제";
        MemberRoleType memberRoleType = MemberRoleType.CUSTOMER;

        // when
        Member member = Member.builder()
            .email(new MemberEmail(email))
            .password(new MemberPassword(password))
            .phoneNumber(new MemberPhoneNumber(phoneNumber))
            .address(new MemberAddress(address))
            .role(new MemberRole(memberRoleType))
            .build();

        // then
        assertAll(
            () -> assertThat(member.getEmail().getEmail()).isEqualTo(email),
            () -> assertThat(member.getPassword().getPassword()).isEqualTo(password),
            () -> assertThat(member.getPhoneNumber().getPhoneNumber()).isEqualTo(phoneNumber),
            () -> assertThat(member.getAddress().getAddress()).isEqualTo(address),
            () -> assertThat(member.getRole().getName()).isEqualTo(memberRoleType)
        );
    }

}
