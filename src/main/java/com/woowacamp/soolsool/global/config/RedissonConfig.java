package com.woowacamp.soolsool.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    private final String redisHost;

    public RedissonConfig(@Value("${redis.host}") final String redisHost) {
        this.redisHost = redisHost;
    }

    @Bean
    public RedissonClient getRedissonConfig() {
        final Config config = new Config();
        config
            .useSingleServer()
            .setAddress(redisHost);

        return Redisson.create(config);
    }
}
