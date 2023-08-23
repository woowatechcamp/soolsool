package com.woowacamp.soolsool.global.auth.util;

import static com.woowacamp.soolsool.global.auth.code.AuthErrorCode.TOKEN_ERROR;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("TokenProvider 단위 테스트")
class TokenProviderTest {


    TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(
            "7Z2R7Z2R7KSR6rCE642w66qo64SI66y07Z6Y65Ok7JeI7Ja0Cg==",
            123456789
        );
    }

    @Test
    @DisplayName("토큰이 정상적으로 추출된다.")
    void validateToken() {
        // given
        final Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L);
        when(member.getRoleName()).thenReturn("구매자");

        String token = tokenProvider.createToken(member);

        // when & then
        assertThatCode(() -> tokenProvider.validateToken(token)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("토큰의 유효기간이 지나면 오류를 반환한다.")
    void validateTokenTestWithTimeOut() {
        // given
        String expired = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjkyNDI4NDk0LCJST0xFX1RZUEUiOiJDVVNUT01FUiIsImV4cCI6MTY5MjQyODQ5NH0.ywzhJeUI3eVxHKE7IlDexJSnb5zTMLJaDxkuQ6kI2h4";

        // when & then
        assertThatCode(() -> tokenProvider.validateToken(expired))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }

    @DisplayName("토큰이 비어있으면, 오류를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void validateTokenTestWithNullAndEmptyKey(String accessKey) {
        // given & when & then
        assertThatCode(() -> tokenProvider.validateToken(accessKey))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }

    @Test
    @DisplayName("토큰이 잘못된 값이면, 오류를 반환한다")
    void validateTokenTestWithWrongKey() {
        // given
        String accessKey = "123";

        // when & then
        assertThatCode(() -> tokenProvider.validateToken(accessKey))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(TOKEN_ERROR.getMessage());
    }
}

