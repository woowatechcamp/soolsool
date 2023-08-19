package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 단위 테스트")
class MemberTest {

    @Test
    @DisplayName("회원을 정상적으로 생성한다.")
    void create() {
        /* given */
        String email = "woowatechcamp@woowafriends.com";
        String password = "q1w2e3r4!";
        String name = "솔라";
        String phoneNumber = "010-1234-5678";
        String mileage = "0";
        String address = "서울 송파구 올림픽로 295 7층";
        MemberRole role = new MemberRole(MemberRoleType.CUSTOMER);

        /* when */
        Member member = Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .mileage(mileage)
            .address(address)
            .role(role)
            .build();

        /* then */
        assertAll(
            () -> assertThat(member.getEmail().getEmail()).isEqualTo(email),
            () -> assertThat(member.getPassword().getPassword()).isEqualTo(password),
            () -> assertThat(member.getName().getName()).isEqualTo(name),
            () -> assertThat(member.getPhoneNumber().getPhoneNumber()).isEqualTo(phoneNumber),
            () -> assertThat(member.getMileage().getMileage()).isEqualTo(mileage),
            () -> assertThat(member.getAddress().getAddress()).isEqualTo(address),
            () -> assertThat(member.getRole()).isEqualTo(role)
        );
    }
}
