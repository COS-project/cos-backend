package com.cos.cercat.redis.certificate.config;

import com.cos.cercat.domain.certificate.Certificate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CertificateRedisConfig {

     @Bean
     public RedisTemplate<String, Certificate> certificateRedisTemplate(
             RedisConnectionFactory redisConnectionFactory) {
         RedisTemplate<String, Certificate> template = new RedisTemplate<>();
         template.setConnectionFactory(redisConnectionFactory);
         template.setKeySerializer(new StringRedisSerializer());
         template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
         return template;
     }

}
