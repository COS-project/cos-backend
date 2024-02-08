package com.cos.cercat.alarm.job;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.repository.AlarmRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.repository.CertificateExamRepository;
import com.cos.cercat.certificate.repository.InterestCertificateRepository;
import com.cos.cercat.user.domain.User;
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
public class AlarmBeforeExamApplicationTasklet implements Tasklet {

    private final CertificateExamRepository certificateExamRepository;
    private final InterestCertificateRepository interestCertificateRepository;
    private final AlarmRepository alarmRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        LocalDateTime currentDateTime = LocalDateTime.now().plusHours(9);
        LocalDateTime oneDayAfter = currentDateTime.plusDays(1);
        int count = 0;

        List<CertificateExam> certificateExams
                = certificateExamRepository.findTomorrowCertificateExams(oneDayAfter, LocalDateTime.now());

        for (CertificateExam certificateExam : certificateExams) {
            Certificate certificate = certificateExam.getCertificate();
            List<InterestCertificate> interestCertificates = interestCertificateRepository.findInterestCertificatesByCertificate(certificate);

            List<User> users = interestCertificates.stream().map(InterestCertificate::getUser).toList();

            count += sendApplicationAlarm(users);
        }
        log.info("AlarmBeforeExamApplicationTasklet - execute: 자격증 시험 신청 기간 알람 {}건 전송 완료, applicationDate : {}", count, oneDayAfter);
        return RepeatStatus.FINISHED;
    }

    private int sendApplicationAlarm(List<User> users) {
        List<Alarm> alarmList = users.stream()
                .map(user -> Alarm.builder()
                        .receiveUser(user)
                        .alarmType(AlarmType.APPLICATION)
                        .isRead(false)
                        .build())
                .toList();

        return alarmRepository.saveAll(alarmList).size();
    }
}
