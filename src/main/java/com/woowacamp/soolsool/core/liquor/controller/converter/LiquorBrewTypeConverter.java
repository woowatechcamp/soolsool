package com.woowacamp.soolsool.core.liquor.controller.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorBrewTypeConverter implements Converter<String, LiquorBrewType> {

    @Override
    public LiquorBrewType convert(final String brewType) {
        return Arrays.stream(LiquorBrewType.values())
            .filter(value -> Objects.equals(value.getType(), brewType))
            .findFirst()
            .orElse(null);
    }
}
