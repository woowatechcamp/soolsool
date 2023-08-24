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

@DisplayName("단위 테스트: MemberName")
class MemberNameTest {

    @Test
    @DisplayName("회원 이름을 정상적으로 생성한다.")
    void create() {
        /* given */
        String name = "솔라";

        /* when & then */
        assertThatCode(() -> new MemberName(name))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 이름이 null 혹은 공백일 경우 SoolSoolException을 던진다.")
    void createFailWithNullOrEmpty(String name) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberName(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 이름은 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 이름이 13자 초과일 경우 SoolSoolException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String name = "a".repeat(14);

        /* when & then */
        assertThatThrownBy(() -> new MemberName(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 이름은 13자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberName origin = new MemberName("솔라라");
        MemberName same = new MemberName("솔라라");
        MemberName different = new MemberName("가나다");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
