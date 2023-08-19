package com.woowacamp.soolsool.core.auth;

import static com.woowacamp.soolsool.global.exception.AuthErrorCode.TOKEN_ERROR;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.auth.util.TokenProvider;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.global.exception.DefaultErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TokenProvider.class)
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = memberRepository.findById(3L)
            .orElseThrow(() -> new SoolSoolException(DefaultErrorCode.MEMBER_NO_INFORMATION));
    }

    @Test
    @DisplayName("토큰이 정상적으로 추출된다.")
    void validateToken() {
        final String token = tokenProvider.createToken(member);
        System.out.println("hello" + token);
        assertThatCode(() -> tokenProvider.validateToken(token)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("토큰의 유효기간이 지나면 오류를 반환한다.")
    void validateTokenTestWithTimeOut() {
        String expired = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjkyNDI4NDk0LCJST0xFX1RZUEUiOiJDVVNUT01FUiIsImV4cCI6MTY5MjQyODQ5NH0.ywzhJeUI3eVxHKE7IlDexJSnb5zTMLJaDxkuQ6kI2h4";
        assertThatCode(() -> tokenProvider.validateToken(expired))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }

    @DisplayName("토큰이 비어있으면, 오류를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void validateTokenTestWithNullAndEmptyKey(String accessKey) {
        assertThatCode(() -> tokenProvider.validateToken(accessKey))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }

    @Test
    @DisplayName("토큰이 잘못된 값이면, 오류를 반환한다")
    void validateTokenTestWithWrongKey() {
        String accessKey = "123";
        assertThatCode(() -> tokenProvider.validateToken(accessKey))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }
}

