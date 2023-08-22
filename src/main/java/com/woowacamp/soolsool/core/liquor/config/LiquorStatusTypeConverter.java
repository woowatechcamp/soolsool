package com.woowacamp.soolsool.core.liquor.config;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorStatusTypeConverter implements Converter<String, LiquorStatusType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public LiquorStatusType convert(final String statusType) {
        return Arrays.stream(LiquorStatusType.values())
            .filter(value -> Objects.equals(value.getStatus(), statusType))
            .findFirst()
            .orElse(null);
    }
}
