package com.cos.cercat.redis;

import com.cos.cercat.alarm.AlarmEvent;
import com.cos.cercat.alarm.AlarmManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final AlarmManager alarmManager;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            AlarmEvent alarmEvent = objectMapper.readValue(message.getBody(), AlarmEvent.class);
            log.info("AlarmMessageListener onMessage: {}", alarmEvent);
            alarmManager.send(alarmEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
