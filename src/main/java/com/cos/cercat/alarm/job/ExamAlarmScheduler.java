package com.cos.cercat.alarm.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExamAlarmScheduler {

    private final JobLauncher jobLauncher;
    private final AlarmBeforeExamApplicationJobConfig alarmBeforeExamApplicationJobConfig;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Scheduled(cron = "0 39 8 * * *")
    public void runExampleJob() {
        //job parameter 설정
        Map<String, JobParameter<?>> confMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        confMap.put("time", new JobParameter<>(date, Date.class));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(alarmBeforeExamApplicationJobConfig.alarmBeforeExamApplicationJob(jobRepository, platformTransactionManager), jobParameters);
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(String.format("ERRER TIME : %s", sdf.format(date)));;
        }
    }

}
