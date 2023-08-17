package com.woowacamp.soolsool.core.member.service;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.dto.request.MemberCreateRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public void addMember(MemberCreateRequest memberCreateRequest) {
        MemberRole memberRole = memberRoleRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("회원 타입 정보가 없습니다."));
        Member member = Member.of(memberRole, memberCreateRequest);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberFindResponse findMember(Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 없습니다."));
        return MemberFindResponse.of(member);
    }

    @Transactional
    public void modifyMember(Long userId, MemberModifyRequest memberModifyRequest) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 없습니다."));
        member.update(memberModifyRequest);
        memberRepository.save(member);
    }

    @Transactional
    public void removeMember(Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 없습니다."));
        memberRepository.delete(member);
    }
}
