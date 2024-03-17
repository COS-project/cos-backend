package com.cos.cercat.global.config;

import com.cos.cercat.alarm.app.consumer.AlarmConsumer;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.post.app.search.DebeziumEvent;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    private String groupId = "cercat";

    @Bean
    public KafkaTemplate<Long, AlarmEvent> alarmKafkaTemplate() {
        return new KafkaTemplate<>(alarmProducerFactory());
    }

    @Bean
    public ProducerFactory<Long, AlarmEvent> alarmProducerFactory() {
        return new DefaultKafkaProducerFactory<>(alarmProducerConfigs());
    }

    @Bean
    public Map<String, Object> alarmProducerConfigs() {
        return ImmutableMap.<String, Object> builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
                .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DebeziumEvent>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DebeziumEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, AlarmEvent>
    alarmEventConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, AlarmEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(alarmConsumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DebeziumEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(DebeziumEvent.class).trustedPackages("*"));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                .put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                .put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                .put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10")
                .build();
    }

    @Bean
    public ConsumerFactory<Long, AlarmEvent> alarmConsumerFactory() {
        JsonDeserializer<AlarmEvent> jsonDeserializer = new JsonDeserializer<>(AlarmEvent.class);
        jsonDeserializer.trustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(
                alarmConsumerConfigs(),
                new LongDeserializer(),
                jsonDeserializer);
    }

    @Bean
    public Map<String, Object> alarmConsumerConfigs() {
        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                .put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                .put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                .put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
                .build();
    }

}
