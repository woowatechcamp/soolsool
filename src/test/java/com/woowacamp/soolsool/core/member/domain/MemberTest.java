package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.domain.vo.MemberName;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPhoneNumber;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 단위 테스트")
class MemberTest {

    @Test
    @DisplayName("회원을 정상적으로 생성한다.")
    void create() {
        /* given */
        MemberEmail email = new MemberEmail("woowatechcamp@woowafriends.com");
        MemberPassword password = new MemberPassword("q1w2e3r4!");
        MemberName name = new MemberName("솔라");
        MemberPhoneNumber phoneNumber = new MemberPhoneNumber("010-1234-5678");
        MemberMileage mileage = new MemberMileage(BigInteger.ZERO);
        MemberAddress address = new MemberAddress("서울 송파구 올림픽로 295 7층");
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
            () -> assertThat(member.getEmail()).isEqualTo(email),
            () -> assertThat(member.getPassword()).isEqualTo(password),
            () -> assertThat(member.getName()).isEqualTo(name),
            () -> assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber),
            () -> assertThat(member.getMileage()).isEqualTo(mileage),
            () -> assertThat(member.getAddress()).isEqualTo(address),
            () -> assertThat(member.getRole()).isEqualTo(role)
        );
    }
}
