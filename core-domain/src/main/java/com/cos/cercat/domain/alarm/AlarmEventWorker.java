package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.common.event.EventStatus;
import com.cos.cercat.domain.common.event.inbox.InboxEventManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventWorker {

    private final InboxEventManager inboxEventManager;
    private final AlarmEventProcessor alarmEventProcessor;

    public void process() {
        inboxEventManager.readByStatus(EventStatus.PENDING)
                .parallelStream()
                .forEach(alarmEventProcessor::process);
    }

}
