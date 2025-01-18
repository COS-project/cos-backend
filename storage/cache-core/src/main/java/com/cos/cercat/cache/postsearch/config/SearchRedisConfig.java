package com.cos.cercat.cache.postsearch.config;

import com.cos.cercat.domain.postsearch.SearchLog;
import com.cos.cercat.domain.postsearch.TrendingKeywordsRanking;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    public RedisTemplate<String, TrendingKeywordsRanking> trendingKeywordRedisTemplate() {
        RedisTemplate<String, TrendingKeywordsRanking> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TrendingKeywordsRanking.class));
        return redisTemplate;
    }

}
