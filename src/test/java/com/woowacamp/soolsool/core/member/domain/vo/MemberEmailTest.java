package com.woowacamp.soolsool.core.member.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 테스트: MemberEmail")
class MemberEmailTest {

    @Test
    @DisplayName("회원 이메일을 정상적으로 생성한다.")
    void create() {
        /* given */
        String email = "woowatechcamp@woowafriends.com";

        /* when & then */
        assertThatCode(() -> new MemberEmail(email))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 이메일이 null 혹은 공백일 경우 SoolSoolException을 던진다.")
    void createFailWithNullOrEmpty(String email) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberEmail(email))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 이메일은 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 이메일이 255자 초과일 경우 SoolSoolException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String email = "a".repeat(239) + "@woowafriends.com"; // length = 256

        /* when & then */
        assertThatThrownBy(() -> new MemberEmail(email))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 이메일은 255자보다 길 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"user.email.com", "user@ema il.com", "user.email@com"})
    @DisplayName("회원 이메일이 표준 이메일 양식이 아닐 경우 SoolSoolException을 던진다.")
    void createFailWithInvalidFormat(String email) {
        /* given */

        /* when & then */
        assertThatCode(() -> new MemberEmail(email))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage("회원 이메일이 표준 이메일 양식에 맞지 않습니다.");
    }


    @Test
    @DisplayName("이메일이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberEmail origin = new MemberEmail("woowacamp@woowafriends.com");
        MemberEmail same = new MemberEmail("woowacamp@woowafriends.com");
        MemberEmail different = new MemberEmail("haha@woowafriends.com");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
