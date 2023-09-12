package com.woowacamp.soolsool.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;

public class CachingConfigurerSupport implements CachingConfigurer {
    @Override
    public CacheManager cacheManager() {
        return CachingConfigurer.super.cacheManager();
    }

    @Override
    public CacheResolver cacheResolver() {
        return CachingConfigurer.super.cacheResolver();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return CachingConfigurer.super.keyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return CachingConfigurer.super.errorHandler();
    }
}
