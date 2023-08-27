package com.woowacamp.soolsool.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.woowacamp.soolsool.global.common.CacheType;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        final List<CaffeineCache> caches = Arrays.stream(CacheType.values()).map(
            cache -> new CaffeineCache(
                cache.getCacheName(),
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                    .maximumSize(cache.getMaximumSize())
                    .build()
            )
        ).collect(Collectors.toList());

        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);

        return cacheManager;
    }
}