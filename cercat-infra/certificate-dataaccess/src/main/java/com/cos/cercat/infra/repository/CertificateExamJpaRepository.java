package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.CertificateExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificateExamJpaRepository extends JpaRepository<CertificateExamEntity, Long> {

    @Query("""
            SELECT ce FROM CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE ce.certificateEntity.id = :certificateId
            ORDER BY ei.examDateTime DESC
            LIMIT 1
            """)
    CertificateExamEntity findRecentCertificateExam(@Param("certificateId") Long certificateId);

    @Query("""
            SELECT ce from CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE FUNCTION('DATE_FORMAT', ei.applicationStartDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :oneDayAfter, '%Y-%m-%d')
            """)
    List<CertificateExamEntity> findTomorrowApplicationCertificateExams(LocalDateTime oneDayAfter);

    @Query("""
            SELECT ce from CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE FUNCTION('DATE_FORMAT', ei.applicationDeadlineDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :oneDayAfter, '%Y-%m-%d')
            """)
    List<CertificateExamEntity> findTomorrowDeadlineCertificateExams(LocalDateTime oneDayAfter);

    @Query("""
            SELECT ce from CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE FUNCTION('DATE_FORMAT', ei.applicationStartDateTime, '%Y-%m-%d') = CURRENT_DATE
            """)
    List<CertificateExamEntity> findTodayApplicationCertificateExams();
}
