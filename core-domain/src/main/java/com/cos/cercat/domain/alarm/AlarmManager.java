package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlarmManager {

    private final AlarmRepository alarmRepository;
    private final EventSender eventSender;
    private final AlarmPublisher alarmPublisher;


    public List<Alarm> read(User user) {
        return alarmRepository.findUnreadAlarms(user);
    }

    public void markAsRead(List<Long> alarmId) {
        alarmRepository.markAsRead(alarmId);
    }

    public int countUnread(User user) {
        return alarmRepository.countUnreadAlarms(user);
    }

    @Transactional
    public void publish(User receiver, User sender, Long targetId, AlarmType alarmType) {
        AlarmEvent alarmEvent = createAlarm(receiver, sender, targetId, alarmType);
        alarmRepository.save(alarmEvent);
        alarmPublisher.publish(alarmEvent);
    }

    public void send(AlarmEvent alarmEvent) {
        eventSender.send(alarmEvent);
    }

    private AlarmEvent createAlarm(User receiver, User sender, Long targetId, AlarmType alarmType) {
        return AlarmEvent.of(receiver, AlarmArg.of(sender, targetId), alarmType);
    }
}
