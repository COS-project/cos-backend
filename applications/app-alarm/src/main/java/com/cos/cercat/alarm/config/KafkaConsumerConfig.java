package com.cos.cercat.alarm.config;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig<V> {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.dead-letter-topic:alarm-events-dlt}")
    private String deadLetterTopic;

    @Value("${kafka.consumer.max-retries:3}")
    private int maxRetries;

    @Value("${kafka.consumer.backoff-initial-interval}")
    private long initialInterval;

    @Value("${kafka.consumer.backoff-max-interval}")
    private long maxInterval;
    
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, V> kafkaListenerContainerFactory(
            KafkaTemplate<String, V> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
        factory.setBatchListener(false);
        factory.setConcurrency(concurrency); // 병렬 소비자 인스턴스 설정
        DefaultErrorHandler errorHandler = configureErrorHandler(kafkaTemplate);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    private DefaultErrorHandler configureErrorHandler(KafkaTemplate<String, V> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition(deadLetterTopic, record.partition()));

        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(maxRetries);
        backOff.setInitialInterval(initialInterval);
        backOff.setMaxInterval(maxInterval);
        backOff.setMultiplier(2.0);

        return new DefaultErrorHandler(recoverer, backOff);
    }

    @Bean
    public ConsumerFactory<String, V> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return new HashMap<>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
            put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000");
            put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "10000");
            put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
            put(JsonDeserializer.TRUSTED_PACKAGES, "*");
            put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        }};
    }

}
