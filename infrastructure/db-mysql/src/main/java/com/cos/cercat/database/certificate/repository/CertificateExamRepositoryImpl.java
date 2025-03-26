package com.cos.cercat.database.certificate.repository;


import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.certificate.entity.CertificateExamEntity;
import com.cos.cercat.database.certificate.entity.ExamInfoEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.CertificateExamRepository;
import com.cos.cercat.domain.certificate.NewExamInformation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CertificateExamRepositoryImpl implements CertificateExamRepository {

    private final CertificateExamJpaRepository certificateExamJpaRepository;

    @Override
    public void save(Certificate certificate, NewExamInformation newExamInfo) {
        ExamInfoEntity examInfoEntity = ExamInfoEntity.builder()
                .examYear(newExamInfo.examYear())
                .round(newExamInfo.round())
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
                .certificateEntity(CertificateEntity.from(certificate))
                .examInfoEntity(examInfoEntity)
                .build();

        certificateExamJpaRepository.save(certificateExamEntity);
    }

    @Override
    public List<CertificateExam> findCertificateExamsByApplicationDate(LocalDateTime applicationDate) {
        List<CertificateExamEntity> certificateExamEntities = certificateExamJpaRepository.findCertificateExamsByApplicationStartDate(applicationDate);
        return certificateExamEntities.stream()
                .map(CertificateExamEntity::toDomain)
                .toList();
    }

    @Override
    public List<CertificateExam> findCertificateExamsByDeadlineDate(LocalDateTime deadlineDate) {
        List<CertificateExamEntity> certificateExamEntities = certificateExamJpaRepository.findCertificateExamsByDeadlineDate(deadlineDate);
        return certificateExamEntities.stream()
                .map(CertificateExamEntity::toDomain)
                .toList();
    }

    @Override
    public CertificateExam findPreviousCertificateExam(Certificate certificate) {
        CertificateExamEntity recentCertificateExam = certificateExamJpaRepository.findPreviousCertificateExam(certificate.id().value());
        return recentCertificateExam.toDomain();
    }

    @Override
    public CertificateExam findNextCertificateExam(Certificate certificate) {
        CertificateExamEntity nextCertificateExam = certificateExamJpaRepository.findNextCertificateExam(certificate.id().value());
        return nextCertificateExam.toDomain();
    }
}
