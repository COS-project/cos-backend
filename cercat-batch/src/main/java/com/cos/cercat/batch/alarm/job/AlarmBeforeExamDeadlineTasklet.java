package com.cos.cercat.batch.alarm.job;


import com.cos.cercat.domain.*;
import com.cos.cercat.repository.AlarmRepository;
import com.cos.cercat.repository.CertificateExamRepository;
import com.cos.cercat.repository.InterestCertificateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmBeforeExamDeadlineTasklet implements Tasklet {

    private final CertificateExamRepository certificateExamRepository;
    private final InterestCertificateRepository interestCertificateRepository;
    private final AlarmRepository alarmRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneDayAfter = currentDateTime.plusDays(1);
        int count = 0;


        List<CertificateExam> certificateExams = certificateExamRepository.findTomorrowDeadlineCertificateExams(oneDayAfter);

        for (CertificateExam certificateExam : certificateExams) {
            Certificate certificate = certificateExam.getCertificate();
            List<InterestCertificate> interestCertificates = interestCertificateRepository.findInterestCertificatesByCertificate(certificate);

            List<User> users = interestCertificates.stream().map(InterestCertificate::getUser).toList();

            count += sendDeadlineAlarm(users, certificateExam);
        }
        log.info("AlarmBeforeExamDeadlineTasklet - execute: 자격증 시험 마감 기간 알람 {}건 전송 완료, DeadlineDate : {}년 {}월 {}일", count, oneDayAfter.getYear(), oneDayAfter.getMonthValue(), oneDayAfter.getDayOfMonth());
        return RepeatStatus.FINISHED;
    }

    private int sendDeadlineAlarm(List<User> users, CertificateExam certificateExam) {
        List<ExamAlarm> alarmList = users.stream()
                .map(user -> ExamAlarm.builder()
                        .receiveUser(user)
                        .alarmType(AlarmType.DEADLINE)
                        .isRead(false)
                        .certificateExam(certificateExam)
                        .build())
                .toList();

        return alarmRepository.saveAll(alarmList).size();
    }
}
