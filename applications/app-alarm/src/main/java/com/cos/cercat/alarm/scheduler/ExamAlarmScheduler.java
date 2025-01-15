package com.cos.cercat.alarm.scheduler;

import com.cos.cercat.domain.certificate.ExamAlarmProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExamAlarmScheduler {

    private final ExamAlarmProcessor examAlarmProcessor;

    @Scheduled(cron = "0 0 9 * * ?")
    public void runSendExamAlarmJob() {
        long startTime = System.currentTimeMillis();
        log.info("Starting exam alarm processing at {}", startTime);
        examAlarmProcessor.process();
        long endTime = System.currentTimeMillis();
        log.info("Finished exam alarm processing at {}. Total time: {} ms", endTime, (endTime - startTime));
    }

}
