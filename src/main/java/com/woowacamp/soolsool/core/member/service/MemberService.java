package com.woowacamp.soolsool.core.member.service;

import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_FOUND_RECEIPT;
import static java.util.Arrays.stream;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberMileageCharge;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberMileageChargeRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.repository.MemberMileageChargeRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleCache;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleCache memberRoleRepository;
    private final MemberMileageChargeRepository memberMileageChargeRepository;

    @Transactional
    public void addMember(final MemberAddRequest memberAddRequest) {
        checkDuplicatedEmail(memberAddRequest.getEmail());

        final MemberRole memberRole = getMemberRole(memberAddRequest.getMemberRoleType());
        final Member member = memberAddRequest.toMember(memberRole);
        memberRepository.save(member);
    }

    private void checkDuplicatedEmail(final String email) {
        final Optional<Member> duplicatedEmil = memberRepository
            .findByEmail(new MemberEmail(email));

        if (duplicatedEmil.isPresent()) {
            throw new SoolSoolException(MemberErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
    }

    private MemberRole getMemberRole(final String memberRequestRoleType) {
        final MemberRoleType memberRoleType = stream(MemberRoleType.values())
            .filter(type -> Objects.equals(type.getType(), memberRequestRoleType))
            .findFirst()
            .orElse(MemberRoleType.CUSTOMER);

        return memberRoleRepository.findByName(memberRoleType)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_ROLE_TYPE));
    }

    @Transactional(readOnly = true)
    public MemberFindResponse findMember(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        return MemberFindResponse.from(member);
    }

    @Transactional
    public void modifyMember(final Long memberId, final MemberModifyRequest memberModifyRequest) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        member.update(memberModifyRequest);
    }

    @Transactional
    public void removeMember(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        memberRepository.delete(member);
    }

    @Transactional
    public void addMemberMileage(
        final Long memberId,
        final MemberMileageChargeRequest memberMileageChargeRequest
    ) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        member.updateMileage(memberMileageChargeRequest.getAmount());

        final MemberMileageCharge memberMileageCharge =
            memberMileageChargeRequest.toMemberMileageCharge(member);
        memberMileageChargeRepository.save(memberMileageCharge);
    }

    @Transactional
    public void subtractMemberMileage(final Long memberId, final MemberMileage mileageUsage) {
        final Member member = memberRepository.findByIdWithLock(memberId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_RECEIPT));

        member.decreaseMileage(mileageUsage);
    }
}
