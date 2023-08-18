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
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.vo.OrderPrice;
import com.woowacamp.soolsool.core.order.domain.vo.OrderQuantity;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatus;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("멤버 마일리지 사용 단위 테스트")
class MemberMileageUsageTest {

    @Test
    @DisplayName("엔티티 정상 생성")
    void createMemberMileage() {
        // given
        String usage = "1000";

        MemberEmail email = new MemberEmail("woowatechcamp@woowafriends.com");
        MemberPassword password = new MemberPassword("q1w2e3r4!");
        MemberName name = new MemberName("솔라");
        MemberPhoneNumber phoneNumber = new MemberPhoneNumber("010-1234-5678");
        MemberMileage mileage = MemberMileage.from("0");
        MemberAddress address = new MemberAddress("서울 송파구 올림픽로 295 7층");
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
            .status(OrderStatus.ORDERED)
            .totalPrice(new OrderPrice(BigInteger.valueOf(1000L)))
            .totalQuantity(new OrderQuantity(10))
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
