package com.woowacamp.soolsool.core.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: MemberRoleType")
class MemberRoleTest {

    @Test
    @DisplayName("회원 역할을 정상적으로 생성한다.")
    void create() {
        /* given */
        MemberRoleType type = MemberRoleType.CUSTOMER;

        /* when & then */
        assertThatCode(() -> new MemberRole(type))
            .doesNotThrowAnyException();
    }
}
