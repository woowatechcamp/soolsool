package com.woowacamp.soolsool.core.member.service;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.dto.request.MemberCreateRequest;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
}
