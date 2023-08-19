package com.woowacamp.soolsool.integration.service;

import static com.woowacamp.soolsool.global.exception.DefaultErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.global.exception.DefaultErrorCode.MEMBER_NO_MATCH_PASSWORD;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.auth.dto.LoginRequest;
import com.woowacamp.soolsool.core.auth.service.AuthService;
import com.woowacamp.soolsool.core.auth.util.TokenProvider;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({AuthService.class, TokenProvider.class})
class AuthServiceTest {


    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("이메일과 비밀번호가 일치하면 token을 발급한다.")
    void createTokenWithEmailAndPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("woowafriends@naver.com", "woowa");

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .doesNotThrowAnyException();
    }


    @Test
    @DisplayName("이메일이 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest(
            "wrongwoowafriends@naver.com",
            "woowa"
        );

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest(
            "woowafriends@naver.com",
            "wrongwoowa"
        );

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_MATCH_PASSWORD.getMessage());
    }
}
