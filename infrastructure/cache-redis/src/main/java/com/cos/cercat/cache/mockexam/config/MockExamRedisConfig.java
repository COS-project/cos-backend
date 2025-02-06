package com.cos.cercat.cache.mockexam.config;

import com.cos.cercat.domain.mockexam.Question;
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


@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class MockExamRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, List<Question>> mockExamRedisTemplate() {
        RedisTemplate<String, List<Question>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, Question.class);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(type));
        return redisTemplate;
    }



}
