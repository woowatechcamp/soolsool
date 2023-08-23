package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 마일리지 충전 단위 테스트")
class MemberMileageChargeTest {

    @Test
    @DisplayName("엔티티 정상 생성")
    void createMemberMileageCharge() {
        // given
        String email = "woowatechcamp@woowafriends.com";
        String password = "q1w2e3r4!";
        String name = "솔라";
        String phoneNumber = "010-1234-5678";
        String mileage = "0";
        String address = "서울 송파구 올림픽로 295 7층";
        MemberRole role = new MemberRole(MemberRoleType.CUSTOMER);
        Member member = Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .mileage(mileage)
            .address(address)
            .role(role)
            .build();
        String charge = "100000";

        // when
        MemberMileageCharge memberMileageCharge = MemberMileageCharge.builder()
            .member(member)
            .amount(charge)
            .build();

        // then
        Assertions.assertAll(
            () -> assertThat(memberMileageCharge.getMember())
                .usingRecursiveComparison().isEqualTo(member),
            () -> assertThat(memberMileageCharge.getAmount().getMileage()).isEqualTo(charge)
        );
    }
}
