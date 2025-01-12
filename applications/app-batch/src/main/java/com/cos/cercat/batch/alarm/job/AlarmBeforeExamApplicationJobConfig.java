//package com.cos.cercat.batch.alarm.job;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@RequiredArgsConstructor
//public class AlarmBeforeExamApplicationJobConfig {
//
//    private final AlarmBeforeExamApplicationTasklet alarmBeforeExamApplicationTasklet;
//    private final AlarmBeforeExamDeadlineTasklet alarmBeforeExamDeadlineTasklet;
//    private final AlarmExamApplicationStartTasklet alarmExamApplicationStartTasklet;
//
//    @Bean
//    public Job sendExamAlarmJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//        return new JobBuilder("sendExamAlarmJob", jobRepository)
//                .start(addApplicationAlarmStep(jobRepository, platformTransactionManager))
//                .next(addDeadlineAlarmStep(jobRepository, platformTransactionManager))
//                .next(addApplicationStartAlarmStep(jobRepository, platformTransactionManager))
//                .build();
//    }
//
//    @Bean
//    public Step addApplicationAlarmStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//        return new StepBuilder("addApplicationAlarmStep", jobRepository)
//                .tasklet(alarmBeforeExamApplicationTasklet, platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step addDeadlineAlarmStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//        return new StepBuilder("addDeadlineAlarmStep", jobRepository)
//                .tasklet(alarmBeforeExamDeadlineTasklet, platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step addApplicationStartAlarmStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//        return new StepBuilder("addApplicationStartAlarmStep", jobRepository)
//                .tasklet(alarmExamApplicationStartTasklet, platformTransactionManager)
//                .build();
//    }
//
//
//}
