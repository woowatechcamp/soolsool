package com.woowacamp.soolsool.core.member.dto.request;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberMileageCharge;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberMileageChargeRequest {

    private BigInteger amount;

    public MemberMileageCharge toMemberMileageCharge(final Member member) {
        return MemberMileageCharge.builder()
            .member(member)
            .amount(amount)
            .build();
    }
}
