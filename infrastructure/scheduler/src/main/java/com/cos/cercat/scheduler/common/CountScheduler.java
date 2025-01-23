package com.cos.cercat.scheduler.common;

import static com.cos.cercat.domain.common.CountSynchronizer.SCHEDULED_FIXED_RATE;

import com.cos.cercat.domain.common.CountSynchronizer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CountScheduler {

    private final List<CountSynchronizer> synchronizers;

    @SchedulerLock(
            name = "count_sync_lock",
            lockAtMostFor = "PT9S",
            lockAtLeastFor = "PT3S"
    )
    @Scheduled(fixedRate = SCHEDULED_FIXED_RATE)
    public void synchronize() {
        synchronizers.forEach(CountSynchronizer::synchronize);
        log.info("CountScheduler.synchronize() executed");
    }

}
