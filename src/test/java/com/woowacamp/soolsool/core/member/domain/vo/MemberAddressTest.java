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

@DisplayName("회원 주소 단위 테스트")
class MemberAddressTest {

    @Test
    @DisplayName("회원 주소를 정상적으로 생성한다.")
    void create() {
        /* given */
        String address = "서울 송파구 올림픽로 295 7층";

        /* when & then */
        assertThatCode(() -> new MemberAddress(address))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 주소가 null 혹은 공백일 경우 ShoppingException을 던진다.")
    void createFailWithNullOrEmpty(String address) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberAddress(address))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 주소는 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 주소가 100자 초과일 경우 ShoppingException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String address = "가".repeat(101);

        /* when & then */
        assertThatThrownBy(() -> new MemberAddress(address))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("회원 주소는 100자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("주소이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberAddress origin = new MemberAddress("서울 송파구 올림픽로 295 7층");
        MemberAddress same = new MemberAddress("서울 송파구 올림픽로 295 7층");
        MemberAddress different = new MemberAddress("대구 동구 동대구로 550");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
