package com.woowacamp.soolsool.core.order.service;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.order.code.OrderErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderMemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void refundMileage(final Long memberId, final BigInteger mileage) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_MEMBER));

        member.updateMileage2(mileage);
    }
}
