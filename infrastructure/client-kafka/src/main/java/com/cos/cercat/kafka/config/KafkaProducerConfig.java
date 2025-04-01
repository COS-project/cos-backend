package com.cos.cercat.kafka.config;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.Map;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaProducerConfig<V> {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
                .put(ProducerConfig.ACKS_CONFIG, "all") // 모든 복제본이 메시지를 받았는지 확인
                .put(ProducerConfig.RETRIES_CONFIG, 10) // 재시도 횟수
                .put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000) // 재시도 간격 (1초)
                .put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000) // 요청 타임아웃 (30초)
                .put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true) // 멱등성 설정으로 중복 전송 방지
                .put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true)
                .build();
    }

    @Bean
    public ProducerFactory<String, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
