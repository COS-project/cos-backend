//package com.cos.cercat.batch.alarm.scheduler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class BatchScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final Job sendExamAlarmJob;
//
//    @Scheduled(cron = "0 0 9 * * ?")
//    public void runSendExamAlarmJob() {
//        try {
//            jobLauncher.run(sendExamAlarmJob, new JobParametersBuilder()
//                    .addLong("timestamp", System.currentTimeMillis())
//                    .toJobParameters());
//            System.out.println("sendExamAlarmJob executed successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error executing sendExamAlarmJob: " + e.getMessage());
//        }
//    }
//
//}
