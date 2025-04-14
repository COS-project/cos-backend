package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.common.event.inbox.InboxEvent;
import com.cos.cercat.domain.common.event.inbox.InboxEventManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AlarmEventProcessor {

    private final AlarmNotifier alarmNotifier;
    private final InboxEventManager inboxEventManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(InboxEvent inboxEvent) {
        Alarm alarm = AlarmFactory.create(inboxEvent.getEvent());

        try {
            inboxEventManager.append(inboxEvent.markAsProcessed());
            alarmNotifier.notifyAlarm(alarm);
        } catch (Exception e) {
            inboxEvent.retry(e.getMessage());
            inboxEventManager.append(inboxEvent);
        }
    }
}
