package com.woowacamp.soolsool.core.liquor.config;

import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptStatusTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LiquorConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new LiquorBrewTypeConverter());
        registry.addConverter(new LiquorRegionTypeConverter());
        registry.addConverter(new LiquorStatusTypeConverter());
        registry.addConverter(new ReceiptStatusTypeConverter());
    }
}
