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

    private final SSESender sseSender;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            sseSender.send(extractReceiverId(message.getChannel()), new String(message.getBody()));
        } catch (Exception e) {
            log.error("Failed to send alarm", e);
        }
    }

    private Long extractReceiverId(byte[] channel) {
        String channelString = new String(channel);
        String[] parts = channelString.split(":");
        return Long.parseLong(parts[1]);
    }
}
