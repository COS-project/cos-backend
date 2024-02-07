package com.cos.cercat.alarm.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class AlarmBeforeExamApplicationJobConfig {

    private final AlarmBeforeExamApplicationTasklet alarmBeforeExamApplicationTasklet;

    @Bean
    public Job alarmBeforeExamApplicationJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("alarmBeforeExamApplicationJob", jobRepository)
                .start(addAlarmStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step addAlarmStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("addAlarmStep", jobRepository)
                .tasklet(alarmBeforeExamApplicationTasklet, platformTransactionManager)
                .build();
    }


}
