package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberRoleCache {

    private final MemberRoleRepository memberRoleRepository;

    @Cacheable(value = "memberRole", key = "#name")
    public Optional<MemberRole> findByName(final MemberRoleType name) {
        log.info("MemberRoleCache {}", name);
        return memberRoleRepository.findByName(name);
    }
}
