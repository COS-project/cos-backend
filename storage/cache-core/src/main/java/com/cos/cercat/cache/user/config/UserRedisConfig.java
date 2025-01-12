package com.cos.cercat.cache.user.config;

import com.cos.cercat.domain.user.RefreshToken;
import com.cos.cercat.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class UserRedisConfig {

  public final static String BAN_TOKEN_KEY = "BAN_TOKEN_KEY";
  public final static Duration USER_CACHE_TTL = Duration.ofDays(1);

  private final RedisConnectionFactory redisConnectionFactory;
  private final ObjectMapper objectMapper;

  @Bean
  public RedisTemplate<String, User> userRedisTemplate() {
    objectMapper.registerModule(new JavaTimeModule());
    RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, User.class));
    return redisTemplate;
  }

  @Bean
  public RedisTemplate<String, RefreshToken> tokenRedisTemplate() {
    RedisTemplate<String, RefreshToken> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RefreshToken.class));
    return redisTemplate;
  }

}
