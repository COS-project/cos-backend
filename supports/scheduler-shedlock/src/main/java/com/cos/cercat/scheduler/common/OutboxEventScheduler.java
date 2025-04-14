package com.cos.cercat.scheduler.common;

import com.cos.cercat.domain.common.event.outbox.OutboxEventWorker;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventScheduler {

    private final OutboxEventWorker outboxEventWorker;

    @SchedulerLock(
            name = "outbox_event_lock",
            lockAtMostFor = "PT9S",
            lockAtLeastFor = "PT3S"
    )
    //
    @Scheduled(fixedRate = 300000)
    public void retryFailedEvents() {
        outboxEventWorker.process();
    }

}
