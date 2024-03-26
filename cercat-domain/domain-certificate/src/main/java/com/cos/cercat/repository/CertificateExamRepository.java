package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificateExamRepository extends JpaRepository<CertificateExam, Long> {

    @Query("""
            SELECT ce FROM CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE ce.certificate.id = :certificateId
            ORDER BY ei.examSchedule.examDateTime DESC
            LIMIT 1
            """)
    CertificateExam findRecentCertificateExam(@Param("certificateId") Long certificateId);

    @Query("""
            SELECT ce from CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE FUNCTION('DATE_FORMAT', ei.examSchedule.applicationStartDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :oneDayAfter, '%Y-%m-%d')
            """)
    List<CertificateExam> findTomorrowApplicationCertificateExams(LocalDateTime oneDayAfter);

    @Query("""
            SELECT ce from CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE FUNCTION('DATE_FORMAT', ei.examSchedule.applicationDeadlineDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :oneDayAfter, '%Y-%m-%d')
            """)
    List<CertificateExam> findTomorrowDeadlineCertificateExams(LocalDateTime oneDayAfter);

    @Query("""
            SELECT ce from CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE FUNCTION('DATE_FORMAT', ei.examSchedule.applicationStartDateTime, '%Y-%m-%d') = CURRENT_DATE
            """)
    List<CertificateExam> findTodayApplicationCertificateExams();
}
