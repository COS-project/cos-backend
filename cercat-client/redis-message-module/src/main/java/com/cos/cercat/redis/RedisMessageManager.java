package com.cos.cercat.redis;

import com.cos.cercat.alarm.*;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;




@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageManager implements AlarmPublisher, AlarmSubscribeManager {

    static String CHANNEL_PREFIX = "ALARM_CHANNEL: ";

    private final RedisTemplate<String, AlarmEvent> redisTemplate;
    private final RedisMessageListenerContainer container;
    private final AlarmMessageListener alarmMessageListener;

    @Override
    public void publish(User receiver, User sender, Long targetId, AlarmType alarmType) {
        AlarmEvent alarm = createAlarm(receiver, sender, targetId, alarmType);
        log.info("RedisMessageManager publish: {}", alarm);
        redisTemplate.convertAndSend(getChannelName(receiver.getId()), alarm);
    }

    @Override
    public void subscribe(Long userId) {
        ChannelTopic channelTopic = ChannelTopic.of(getChannelName(userId));
        log.info("RedisMessageManager subscribe: {}", channelTopic);
        container.addMessageListener(alarmMessageListener, channelTopic);
    }

    public void unsubscribe(Long userId) {
        ChannelTopic channelTopic = ChannelTopic.of(getChannelName(userId));
        log.info("RedisMessageManager unsubscribe: {}", channelTopic);
        container.removeMessageListener(alarmMessageListener, channelTopic);
    }

    private AlarmEvent createAlarm(User receiver, User sender, Long targetId, AlarmType alarmType) {
        return AlarmEvent.of(receiver, AlarmArg.of(sender, targetId), alarmType);
    }

    private String getChannelName(Long userId) {
        return CHANNEL_PREFIX + userId;
    }


}
