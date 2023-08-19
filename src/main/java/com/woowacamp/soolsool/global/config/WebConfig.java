package com.woowacamp.soolsool.global.config;

import com.woowacamp.soolsool.core.liquor.controller.converter.LiquorBrewTypeConverter;
import com.woowacamp.soolsool.core.liquor.controller.converter.LiquorRegionTypeConverter;
import com.woowacamp.soolsool.core.liquor.controller.converter.LiquorStatusTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new LiquorBrewTypeConverter());
        registry.addConverter(new LiquorRegionTypeConverter());
        registry.addConverter(new LiquorStatusTypeConverter());
    }
}
