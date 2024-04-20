package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.ExamInfoEntity;
import com.cos.cercat.domain.certificate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CertificateExamCoreRepository implements CertificateExamRepository {

    private final CertificateExamJpaRepository certificateExamJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;

    @Override
    public void save(TargetCertificate targetCertificate, ExamInformation examInfo) {
        CertificateEntity certificateEntity = getCertificateEntity(targetCertificate);

        ExamInfoEntity examInfoEntity = ExamInfoEntity.builder()

                .examYear(examInfo.examYear())
                .round(examInfo.round())
                .applicationStartDateTime(examInfo.examSchedule().applicationStartDateTime())
                .applicationDeadlineDateTime(examInfo.examSchedule().applicationDeadlineDateTime())
                .resultAnnouncementDateTime(examInfo.examSchedule().ResultAnnouncementDateTime())
                .examDateTime(examInfo.examSchedule().examDateTime())
                .writtenExamFee(examInfo.examFee().writtenExamFee())
                .practicalExamFee(examInfo.examFee().practicalExamFee())
                .writtenExamTimeLimit(examInfo.examTimeLimit().writtenExamTimeLimit())
                .practicalExamTimeLimit(examInfo.examTimeLimit().practicalExamTimeLimit())
                .subjectPassingCriteria(examInfo.passingCriteria().subjectPassingCriteria())
                .totalAvgCriteria(examInfo.passingCriteria().totalAvgCriteria())
                .practicalPassingCriteria(examInfo.passingCriteria().practicalPassingCriteria())
                .subjectsInfo(examInfo.subjectsInfo())
                .description(examInfo.description())
                .examFormat(examInfo.examFormat())
                .examEligibility(examInfo.examEligibility())
                .build();

        CertificateExamEntity certificateExamEntity = CertificateExamEntity.builder()
                .certificateEntity(certificateEntity)
                .examInfoEntity(examInfoEntity)
                .build();

        certificateExamJpaRepository.save(certificateExamEntity);
    }

    @Override
    public CertificateExam findRecentCertificateExam(TargetCertificate targetCertificate) {
        CertificateExamEntity recentCertificateExam = certificateExamJpaRepository.findRecentCertificateExam(targetCertificate.certificateId());
        return recentCertificateExam.toDomain();
    }

    private CertificateEntity getCertificateEntity(TargetCertificate target) {
        return certificateJpaRepository.findById(target.certificateId())
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found"));
    }
}
