package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberRoleCache {

    private final MemberRoleRepository memberRoleRepository;

    @Cacheable(value = "memberRole", key = "#name", unless = "#result==null")
    public Optional<MemberRole> findByName(final MemberRoleType name) {
        log.info("MemberRoleCache findByName");
        return memberRoleRepository.findByName(name);
    }
}
