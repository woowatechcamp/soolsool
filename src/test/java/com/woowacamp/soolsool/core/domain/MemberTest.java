package com.woowacamp.soolsool.core.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MemberEntityTest")
class MemberTest {

    @Test
    @DisplayName("Member Entity 객체 생성 성공")
    void createMemberEntity() {
        // given
        String email = "test@email.com";
        String password = "test_password";
        String phoneNumber = "010-1234-5678";
        String address = "서울시 잠실 우아한 형제";
        MemberRole memberRole = MemberRole.CUSTOMER;

        // when
        Member member = Member.builder()
            .email(email)
            .password(password)
            .phoneNumber(phoneNumber)
            .address(address)
            .role(memberRole)
            .build();

        // then
        assertAll(
            () -> assertThat(member.getEmail()).isEqualTo(email),
            () -> assertThat(member.getPassword()).isEqualTo(password),
            () -> assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber),
            () -> assertThat(member.getAddress()).isEqualTo(address),
            () -> assertThat(member.getRole()).isEqualTo(memberRole)
        );
    }

}
