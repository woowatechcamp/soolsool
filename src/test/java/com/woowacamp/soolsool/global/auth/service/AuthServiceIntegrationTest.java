package com.woowacamp.soolsool.global.auth.service;

import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_MATCH_PASSWORD;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.util.TokenProvider;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({AuthService.class, TokenProvider.class})
@DisplayName("AuthService 통합 테스트")
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("이메일과 비밀번호가 일치하면 token을 발급한다.")
    void createTokenWithEmailAndPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("kim@email.com", "baedal");

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("이메일이 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest(
            "wrong@email.com",
            "baedal"
        );

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("비밀번호가 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest(
            "kim@email.com",
            "wrong"
        );

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_MATCH_PASSWORD.getMessage());
    }
}
