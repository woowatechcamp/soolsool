package com.woowacamp.soolsool.core.member.service;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import com.woowacamp.soolsool.global.exception.DefaultErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public void addMember(final MemberAddRequest memberAddRequest) {
        MemberRole memberRole = memberRoleRepository.findById(1L)
            .orElseThrow(() -> new SoolSoolException(DefaultErrorCode.MEMBER_NO_ROLE_TYPE));
        Member member = Member.of(memberRole, memberAddRequest);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberFindResponse findMember(final Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new SoolSoolException(DefaultErrorCode.MEMBER_NO_INFORMATION));
        return MemberFindResponse.of(member);
    }

    @Transactional
    public void modifyMember(final Long userId, final MemberModifyRequest memberModifyRequest) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new SoolSoolException(DefaultErrorCode.MEMBER_NO_INFORMATION));
        member.update(memberModifyRequest);
        memberRepository.save(member);
    }

    @Transactional
    public void removeMember(final Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new SoolSoolException(DefaultErrorCode.MEMBER_NO_INFORMATION));
        memberRepository.delete(member);
    }
}
