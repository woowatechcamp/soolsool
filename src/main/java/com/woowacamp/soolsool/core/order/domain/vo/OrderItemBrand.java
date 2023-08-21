package com.woowacamp.soolsool.core.order.domain.vo;


import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class OrderItemBrand {

    private final String brand;

    @Getter
    @EqualsAndHashCode
    public static class OrderMileageUsage {

        private final BigInteger mileage;

        public static OrderMileageUsage from(final String mileage) {
            return new OrderMileageUsage(new BigInteger(mileage));
        }

        public OrderMileageUsage(final BigInteger mileage) {
            validateIsNotNull(mileage);
            validateIsValidSize(mileage);

            this.mileage = mileage;
        }

        private void validateIsValidSize(final BigInteger mileage) {
            if (mileage.compareTo(BigInteger.ZERO) < 0) {
                throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
            }
        }

        private void validateIsNotNull(final BigInteger mileage) {
            if (Objects.isNull(mileage)) {
                throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
            }
        }
    }
}
