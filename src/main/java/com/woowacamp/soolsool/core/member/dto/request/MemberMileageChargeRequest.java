package com.woowacamp.soolsool.core.member.dto.request;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberMileageCharge;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberMileageChargeRequest {

    @Size(max = 255, message = "불가능한 마일리지 액수 입니다.")
    private final String amount;

    public MemberMileageCharge toMemberMileageCharge(final Member member) {
        return MemberMileageCharge.builder()
            .member(member)
            .amount(this.amount)
            .build();
    }
}
