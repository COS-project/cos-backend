package com.cos.cercat.batch.alarm.job;


import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.infra.entity.CertificateEntity;
import com.cos.cercat.infra.entity.CertificateExamEntity;
import com.cos.cercat.infra.entity.ExamAlarmEntity;
import com.cos.cercat.infra.entity.InterestCertificateEntity;
import com.cos.cercat.infra.entity.UserEntity;
import com.cos.cercat.infra.repository.AlarmJpaRepository;
import com.cos.cercat.infra.repository.CertificateExamJpaRepository;
import com.cos.cercat.infra.repository.InterestCertificateJpaRepository;
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
public class AlarmExamApplicationStartTasklet implements Tasklet {

    private final CertificateExamJpaRepository certificateExamJpaRepository;
    private final InterestCertificateJpaRepository interestCertificateJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        int count = 0;
        List<CertificateExamEntity> certificateExamEntities = certificateExamJpaRepository.findTodayApplicationCertificateExams();

        for (CertificateExamEntity certificateExamEntity : certificateExamEntities) {
            CertificateEntity certificateEntity = certificateExamEntity.getCertificateEntity();
            List<InterestCertificateEntity> interestCertificateEntities = interestCertificateJpaRepository.findInterestCertificatesByCertificateId(
                    certificateEntity.getId());

            List<UserEntity> userEntities = interestCertificateEntities.stream()
                    .map(interestCertificateEntity ->
                            interestCertificateEntity.getInterestCertificatePK().getUserEntity())
                    .toList();

            count += sendApplicationAlarm(userEntities, certificateExamEntity);
        }
        log.info(
                "AlarmExamApplicationStartTasklet - execute: 자격증 시험 신청 시작 알람 {}건 전송 완료, ApplicaionDate : {}년 {}월 {}일",
                count, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth());
        return RepeatStatus.FINISHED;
    }

    public int sendApplicationAlarm(List<UserEntity> userEntities,
            CertificateExamEntity certificateExamEntity) {
        List<ExamAlarmEntity> alarmList = userEntities.stream()
                .map(user -> ExamAlarmEntity.builder()
                        .receiveUserEntity(user)
                        .alarmType(AlarmType.START_APPLICATION)
                        .isRead(false)
                        .certificateExamEntity(certificateExamEntity)
                        .build())
                .toList();

        return alarmJpaRepository.saveAll(alarmList).size();
    }
}
