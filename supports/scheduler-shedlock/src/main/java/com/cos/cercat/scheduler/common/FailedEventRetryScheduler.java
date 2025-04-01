package com.cos.cercat.scheduler.common;

import com.cos.cercat.domain.common.FailedEventRetryer;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FailedEventRetryScheduler {

    private final FailedEventRetryer failedEventRetryer;

    @SchedulerLock(
            name = "event_retry_lock",
            lockAtMostFor = "PT9S",
            lockAtLeastFor = "PT3S"
    )
    @Scheduled(fixedRate = 1800000)
    public void retryFailedEvents() {
        failedEventRetryer.retry();
    }

}
