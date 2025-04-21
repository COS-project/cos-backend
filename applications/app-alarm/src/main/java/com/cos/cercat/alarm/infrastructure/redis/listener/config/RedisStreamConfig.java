package com.cos.cercat.alarm.infrastructure.redis.listener.config;

import com.cos.cercat.alarm.infrastructure.redis.listener.AlarmEventRedisStreamListener;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.util.ErrorHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisStreamConfig implements DisposableBean{
    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> container;
    private final RedisConnectionFactory redisConnectionFactory;
    private final AlarmEventRedisStreamListener streamListener;
    private final StringRedisTemplate redisTemplate;
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();
    private final ErrorHandler shutdownIgnoringErrorHandler;

    @Value("#{'${redis.key-prefix.alarm-events}'.split(',')}")
    private List<String> eventTypes;

    @Value("${redis.consumer-group.alarm}")
    private String group;

    @Value("${spring.application.name}")
    private String consumerName;

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer() {
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainerOptions.builder()
                        .batchSize(100)
                        .pollTimeout(Duration.ofSeconds(5))
                        .errorHandler(shutdownIgnoringErrorHandler)
                        .build();

        container = StreamMessageListenerContainer.create(redisConnectionFactory, options);


        // 각 이벤트 타입별로 구독
        eventTypes.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(type -> {
                    String key = "event:" + type;
                    createGroupIfAbsent(key);

                    Subscription subscription = container.receive(
                            Consumer.from(group, consumerName),
                            StreamOffset.create(key, ReadOffset.lastConsumed()),
                            streamListener
                    );
                    subscriptions.add(subscription);
                    log.info("구독 시작 – key={}", key);
                });

        container.start();
        log.info("Redis Stream 컨슈머 시작 – group={}, consumer={}", group, consumerName);
        return container;
    }

    @Override
    public void destroy() {
        subscriptions.forEach(Subscription::cancel);
        subscriptions.clear();
        if (container != null) {
            container.stop();
            log.info("Redis Stream 컨슈머 중지 – group={}, consumer={}", group, consumerName);
        }
    }

    private void createGroupIfAbsent(String key) {
        try {
            StreamOperations<String, Object, Object> streamOps = redisTemplate.opsForStream();
            streamOps.createGroup(key, group);
            log.info("컨슈머 그룹 생성 – key={}, group={}", key, group);
        } catch (Exception e) {
            // 이미 그룹이 존재하는 경우 무시
        }
    }
}
