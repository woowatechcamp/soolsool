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

@DisplayName("회원 전화번호 단위 테스트")
class MemberPhoneNumberTest {

    @Test
    @DisplayName("회원 전화번호를 정상적으로 생성한다.")
    void create() {
        /* given */
        String phoneNumber = "010-1234-5678";

        /* when & then */
        assertThatCode(() -> new MemberPhoneNumber(phoneNumber))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 전화번호가 null 혹은 공백일 경우 ShoppingException을 던진다.")
    void createFailWithNullOrEmpty(String phoneNumber) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberPhoneNumber(phoneNumber))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 전화번호는 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 전화번호가 13자 초과일 경우 ShoppingException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String phoneNumber = "a".repeat(61);

        /* when & then */
        assertThatThrownBy(() -> new MemberPhoneNumber(phoneNumber))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 전화번호는 13자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("전화번호가 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberPhoneNumber origin = new MemberPhoneNumber("010-1234-5678");
        MemberPhoneNumber same = new MemberPhoneNumber("010-1234-5678");
        MemberPhoneNumber different = new MemberPhoneNumber("010-1111-1111");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
