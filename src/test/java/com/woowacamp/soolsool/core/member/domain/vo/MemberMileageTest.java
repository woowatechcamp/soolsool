package com.woowacamp.soolsool.core.member.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: MemberMileage")
class MemberMileageTest {

    @Test
    @DisplayName("회원 마일리지를 정상적으로 생성한다.")
    void create() {
        /* given */
        BigInteger mileage = BigInteger.valueOf(10_000L);

        /* when & then */
        assertThatCode(() -> new MemberMileage(mileage))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 마일리지가 0 미만일 경우 SoolSoolException을 던진다.")
    void createFailWithInvalidMileage() {
        /* given */
        BigInteger mileage = BigInteger.valueOf(-1L);

        /* when & then */
        assertThatThrownBy(() -> new MemberMileage(mileage))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 마일리지는 0 미만일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 마일리지가 null일 경우 SoolSoolException을 던진다.")
    void createFail() {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new MemberMileage(null))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("회원 마일리지는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("회원 마일리지가 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        MemberMileage origin = new MemberMileage(BigInteger.ONE);
        MemberMileage same = new MemberMileage(BigInteger.ONE);
        MemberMileage different = new MemberMileage(BigInteger.ZERO);

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );

    }
}
