package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

}
