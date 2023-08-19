package com.woowacamp.soolsool.core.liquor.controller.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;

public class LiquorRegionTypeConverter implements Converter<String, LiquorRegionType> {

    @Override
    public LiquorRegionType convert(final String regionType) {
        return Arrays.stream(LiquorRegionType.values())
            .filter(value -> Objects.equals(value.getName(), regionType))
            .findFirst()
            .orElse(null);
    }
}
