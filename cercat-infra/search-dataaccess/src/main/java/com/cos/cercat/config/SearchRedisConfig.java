package com.cos.cercat.config;

import com.cos.cercat.domain.search.SearchLog;
import com.cos.cercat.domain.search.TrendingKeyword;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@EnableRedisRepositories
@Configuration
@RequiredArgsConstructor
public class SearchRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, SearchLog> searchLogRedisTemplate() {
        RedisTemplate<String, SearchLog> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SearchLog.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, List<TrendingKeyword>> trendingKeywordRedisTemplate() {
        RedisTemplate<String, List<TrendingKeyword>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, TrendingKeyword.class);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(type));
        return redisTemplate;
    }

}
