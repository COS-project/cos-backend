package com.cos.cercat.batch.alarm.job;

import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.*;
import com.cos.cercat.repository.AlarmJpaRepository;
import com.cos.cercat.repository.CertificateExamJpaRepository;
import com.cos.cercat.repository.InterestCertificateJpaRepository;
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

    private final CertificateExamJpaRepository certificateExamJpaRepository;
    private final InterestCertificateJpaRepository interestCertificateJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneDayAfter = currentDateTime.plusDays(1);
        int count = 0;



        List<CertificateExamEntity> certificateExamEntities = certificateExamJpaRepository.findTomorrowApplicationCertificateExams(oneDayAfter);

        for (CertificateExamEntity certificateExamEntity : certificateExamEntities) {
            CertificateEntity certificateEntity = certificateExamEntity.getCertificateEntity();
            List<InterestCertificateEntity> interestCertificateEntities = interestCertificateJpaRepository.findInterestCertificatesByCertificateId(certificateEntity.getId());

            List<UserEntity> userEntities = interestCertificateEntities.stream().map(interestCertificateEntity ->
                            interestCertificateEntity.getInterestCertificatePK().getUserEntity())
                    .toList();

            count += sendApplicationAlarm(userEntities, certificateExamEntity);
        }

        log.info("AlarmBeforeExamApplicationTasklet - execute: 자격증 시험 신청 기간 알람 {}건 전송 완료, DeadlineDate : {}년 {}월 {}일", count, oneDayAfter.getYear(), oneDayAfter.getMonthValue(), oneDayAfter.getDayOfMonth());
        return RepeatStatus.FINISHED;
    }

    public int sendApplicationAlarm(List<UserEntity> userEntities, CertificateExamEntity certificateExamEntity) {
        List<ExamAlarmEntity> alarmList = userEntities.stream()
                .map(user -> ExamAlarmEntity.builder()
                        .receiveUserEntity(user)
                        .alarmType(AlarmType.BEFORE_APPLICATION)
                        .isRead(false)
                        .certificateExamEntity(certificateExamEntity)
                        .build())
                .toList();

        return alarmJpaRepository.saveAll(alarmList).size();
    }
}
