package com.cos.cercat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@EnableRedisRepositories
@Configuration
@RequiredArgsConstructor
public class SseRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, SseEmitter> sseEmitterRedisTemplate() {
        RedisTemplate<String, SseEmitter> sseEmitterRedisTemplate = new RedisTemplate<>();
        sseEmitterRedisTemplate.setConnectionFactory(redisConnectionFactory);
        sseEmitterRedisTemplate.setKeySerializer(new StringRedisSerializer());
        sseEmitterRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SseEmitter.class));
        return sseEmitterRedisTemplate;
    }


}

