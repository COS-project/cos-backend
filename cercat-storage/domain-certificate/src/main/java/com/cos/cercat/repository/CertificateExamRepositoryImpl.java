package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.ExamInfoEntity;
import com.cos.cercat.domain.certificate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CertificateExamRepositoryImpl implements CertificateExamRepository {

    private final CertificateExamJpaRepository certificateExamJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;

    @Override
    public void save(TargetCertificate targetCertificate, NewExamInformation newExamInfo) {
        CertificateEntity certificateEntity = getCertificateEntity(targetCertificate);
        ExamInfoEntity examInfoEntity = ExamInfoEntity.builder()
                .examYear(newExamInfo.mockExamSession().examYear())
                .round(newExamInfo.mockExamSession().round())
                .applicationStartDateTime(newExamInfo.examSchedule().applicationStartDateTime())
                .applicationDeadlineDateTime(newExamInfo.examSchedule().applicationDeadlineDateTime())
                .resultAnnouncementDateTime(newExamInfo.examSchedule().resultAnnouncementDateTime())
                .examDateTime(newExamInfo.examSchedule().examDateTime())
                .writtenExamFee(newExamInfo.examFee().writtenExamFee())
                .practicalExamFee(newExamInfo.examFee().practicalExamFee())
                .writtenExamTimeLimit(newExamInfo.examTimeLimit().writtenExamTimeLimit())
                .practicalExamTimeLimit(newExamInfo.examTimeLimit().practicalExamTimeLimit())
                .subjectPassingCriteria(newExamInfo.passingCriteria().subjectPassingCriteria())
                .totalAvgCriteria(newExamInfo.passingCriteria().totalAvgCriteria())
                .practicalPassingCriteria(newExamInfo.passingCriteria().practicalPassingCriteria())
                .subjectsInfo(newExamInfo.subjectsInfo())
                .description(newExamInfo.description())
                .examFormat(newExamInfo.examFormat())
                .examEligibility(newExamInfo.examEligibility())
                .build();

        CertificateExamEntity certificateExamEntity = CertificateExamEntity.builder()
                .certificateEntity(certificateEntity)
                .examInfoEntity(examInfoEntity)
                .build();

        certificateExamJpaRepository.save(certificateExamEntity);
    }

    @Override
    public CertificateExam findRecentCertificateExam(Certificate certificate) {
        CertificateExamEntity recentCertificateExam = certificateExamJpaRepository.findRecentCertificateExam(certificate.id());
        return recentCertificateExam.toDomain();
    }

    private CertificateEntity getCertificateEntity(TargetCertificate target) {
        return certificateJpaRepository.findById(target.certificateId())
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found"));
    }
}
