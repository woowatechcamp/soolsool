package com.woowacamp.soolsool.core.receipt;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class ReceiptStatusTypeConverter implements Converter<String, ReceiptStatusType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public ReceiptStatusType convert(final String statusType) {
        return Arrays.stream(ReceiptStatusType.values())
            .filter(value -> Objects.equals(value.getName(), statusType))
            .findFirst()
            .orElse(null);
    }
}
