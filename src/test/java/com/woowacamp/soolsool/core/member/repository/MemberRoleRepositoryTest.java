package com.woowacamp.soolsool.core.member.repository;

import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/member-type.sql")
@DisplayName("회원 타입 repository 통합 테스트")
class MemberRoleRepositoryTest {

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Test
    @DisplayName("판매자로 memberType 을 조회한다.")
    void findByVendor() {
        // given
        MemberRoleType memberRoleType = MemberRoleType.VENDOR;

        // when
        MemberRole memberRole = memberRoleRepository.findByName(memberRoleType).get();

        // then
        Assertions.assertThat(memberRole.getName()).isEqualTo(memberRole.getName());
    }

    @Test
    @DisplayName("구매자로 memberType 을 조회한다.")
    void findByCustomer() {
        // given
        MemberRoleType memberRoleType = MemberRoleType.CUSTOMER;

        // when
        MemberRole memberRole = memberRoleRepository.findByName(memberRoleType).get();

        // then
        Assertions.assertThat(memberRole.getName()).isEqualTo(memberRole.getName());
    }
}
