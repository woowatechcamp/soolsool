package com.woowacamp.soolsool.core.liquor.config;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorRegionTypeConverter implements Converter<String, LiquorRegionType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public LiquorRegionType convert(final String regionType) {
        return Arrays.stream(LiquorRegionType.values())
            .filter(value -> Objects.equals(value.getName(), regionType))
            .findFirst()
            .orElse(null);
    }
}
