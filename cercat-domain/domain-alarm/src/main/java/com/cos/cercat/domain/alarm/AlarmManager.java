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


  public List<Alarm> read(User user) {
    return alarmRepository.findUnreadAlarms(user);
  }

  @Transactional
  public void markAsRead(User user) {
    alarmRepository.markAsRead(user);
  }

  public int countUnread(User user) {
    return alarmRepository.countUnreadAlarms(user);
  }

  public void send(AlarmEvent alarmEvent) {
    alarmRepository.save(alarmEvent);
    eventSender.send(alarmEvent);
  }
}
