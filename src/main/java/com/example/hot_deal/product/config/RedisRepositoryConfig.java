package com.example.hot_deal.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.example.hot_deal.product.domain.repository.redis")
public class RedisRepositoryConfig {
}
