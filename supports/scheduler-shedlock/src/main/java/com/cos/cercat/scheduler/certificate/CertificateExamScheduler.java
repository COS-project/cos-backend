package com.cos.cercat.scheduler.certificate;

import com.cos.cercat.domain.certificate.CertificateExamScheduleChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateExamScheduler {

    private final CertificateExamScheduleChecker certificateExamScheduleChecker;

    @Scheduled(cron = "0 0 9 * * ?")
    @SchedulerLock(name = "exam_schedule_check", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void runExamScheduleCheck() {
        certificateExamScheduleChecker.check();
        log.info("CertificateExamScheduler.runScheduleCheck() executed");
    }

}
