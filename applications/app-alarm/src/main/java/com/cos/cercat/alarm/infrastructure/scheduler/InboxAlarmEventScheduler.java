package com.cos.cercat.alarm.infrastructure.scheduler;

import com.cos.cercat.domain.alarm.AlarmEventWorker;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InboxAlarmEventScheduler {

    private final AlarmEventWorker alarmEventWorker;

    @SchedulerLock(
            name = "inbox_event_lock",
            lockAtMostFor = "PT9S",
            lockAtLeastFor = "PT3S"
    )
    @Scheduled(fixedRate = 5000)
    public void processInboxAlarmEvents() { alarmEventWorker.process(); }

}
