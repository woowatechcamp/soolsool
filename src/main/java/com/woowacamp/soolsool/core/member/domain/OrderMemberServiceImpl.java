package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.order.service.OrderMemberService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderMemberServiceImpl implements OrderMemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void refundMileage(final Long memberId, final BigInteger mileage) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        member.updateMileage(mileage);
    }
}
