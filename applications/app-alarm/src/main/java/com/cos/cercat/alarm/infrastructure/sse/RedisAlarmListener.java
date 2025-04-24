package com.cos.cercat.alarm.infrastructure.sse;

import com.cos.cercat.domain.alarm.Alarm;
import com.fasterxml.jackson.databind.
        ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisAlarmListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SSESender sseSender;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("Received message {}", message.getBody());
            Alarm alarm = objectMapper.readValue(message.getBody(), Alarm.class);
            sseSender.send(alarm);
        } catch (Exception e) {
            log.error("Failed to send alarm", e);
        }
    }
}
