package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: MemberMileageUsage")
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
        OrderStatus orderStatus = mock(OrderStatus.class);
        Receipt receipt = mock(Receipt.class);

        Order order = Order.builder()
            .memberId(1L)
            .orderStatus(orderStatus)
            .receipt(receipt)
            .build();

        // when
        MemberMileageUsage memberMileageUsage = MemberMileageUsage.builder()
            .member(member)
            .order(order)
            .amount(new BigInteger(usage))
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
