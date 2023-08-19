package com.woowacamp.soolsool.core.liquor.controller.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorStatusTypeConverter implements Converter<String, LiquorStatusType> {

    @Override
    public LiquorStatusType convert(final String statusType) {
        return Arrays.stream(LiquorStatusType.values())
            .filter(value -> Objects.equals(value.getStatus(), statusType))
            .findFirst()
            .orElse(null);
    }
}
