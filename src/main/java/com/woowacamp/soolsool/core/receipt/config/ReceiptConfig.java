package com.woowacamp.soolsool.core.receipt.config;

import com.woowacamp.soolsool.core.receipt.ReceiptStatusTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReceiptConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new ReceiptStatusTypeConverter());
    }
}
