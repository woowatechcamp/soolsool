package com.woowacamp.soolsool.core.member.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("회원 비밀번호 단위 테스트")
class MemberPasswordTest {

    @Test
    @DisplayName("회원 비밀번호를 정상적으로 생성한다.")
    void create() {
        /* given */
        String password = "q1w2e3r4!";

        /* when & then */
        assertThatCode(() -> new MemberPassword(password))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 비밀번호가 null 혹은 공백일 경우 ShoppingException을 던진다.")
    void createFailWithNullOrEmpty(String password) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberPassword(password))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 비밀번호는 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 비밀번호가 60자 초과일 경우 ShoppingException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String password = "a".repeat(61);

        /* when & then */
        assertThatThrownBy(() -> new MemberPassword(password))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 비밀번호은 60자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberPassword origin = new MemberPassword("솔라라");
        MemberPassword same = new MemberPassword("솔라라");
        MemberPassword different = new MemberPassword("가나다");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
