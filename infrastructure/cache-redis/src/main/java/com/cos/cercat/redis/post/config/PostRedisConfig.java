package com.cos.cercat.redis.post.config;

import com.cos.cercat.domain.post.Post;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class PostRedisConfig {

    private final ObjectMapper objectMapper;

     @Bean
     public RedisTemplate<String, List<Post>> postRedisTemplate(
             RedisConnectionFactory redisConnectionFactory) {
         objectMapper.registerModule(new JavaTimeModule());
         RedisTemplate<String, List<Post>> template = new RedisTemplate<>();
         template.setConnectionFactory(redisConnectionFactory);
         template.setKeySerializer(new StringRedisSerializer());
         JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, Post.class);
         template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, type));
         return template;
     }

}
