package com.sparta.icticket.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisConfig {
    @Value("${REDIS_HOST}")
    private String host;
    @Value("${REDIS_PORT}")
    private int port;
    @Value("${REDIS_PASSWORD}")
    private String password;
    private static final String REDISSON_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_PREFIX + host + ":" + port)
                .setPassword(password);

        return Redisson.create(config);
    }
}