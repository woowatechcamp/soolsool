package com.woowacamp.soolsool.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RedissonConfig {

    private final String host;
    private final String port;

    public RedissonConfig(
        @Value("${spring.redis.host}") final String host,
        @Value("${spring.redis.port}") final String port
    ) {
        this.host = host;
        this.port = port;
    }

    @Bean
    public RedissonClient getRedissonConfig() {
        final Config config = new Config();
        config
            .useSingleServer()
            .setAddress("redis://" + host + ":" + port);

        return Redisson.create(config);
    }
}
