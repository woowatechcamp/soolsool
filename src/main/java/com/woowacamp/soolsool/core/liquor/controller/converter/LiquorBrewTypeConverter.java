package com.woowacamp.soolsool.core.liquor.controller.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorBrewTypeConverter implements Converter<String, LiquorBrewType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public LiquorBrewType convert(final String brewType) {
        return Arrays.stream(LiquorBrewType.values())
            .filter(value -> Objects.equals(value.getName(), brewType))
            .findFirst()
            .orElse(null);
    }
}
