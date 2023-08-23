package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.order.domain.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 마일리지 사용 단위 테스트")
class MemberMileageUsageTest {

    @Test
    @DisplayName("엔티티 정상 생성")
    void createMemberMileage() {
        // given
        String usage = "1000";

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

        Order order = Order.builder()
            .memberId(1L)
            .build();

        // when
        MemberMileageUsage memberMileageUsage = MemberMileageUsage.builder()
            .member(member)
            .order(order)
            .amount(usage)
            .build();

        // then
        assertAll(
            () -> assertThat(memberMileageUsage.getMember())
                .usingRecursiveComparison().isEqualTo(member),
            () -> assertThat(memberMileageUsage.getOrder())
                .usingRecursiveComparison().isEqualTo(order),
            () -> assertThat(memberMileageUsage.getAmount().getMileage())
                .isEqualTo(usage)
        );
    }
}
