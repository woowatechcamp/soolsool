package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
