package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRoleCache {

    private final MemberRoleRepository memberRoleRepository;

    @Cacheable(value = "memberRole", key = "#name")
    public Optional<MemberRole> findByName(final MemberRoleType name) {
        return memberRoleRepository.findByName(name);
    }
}
