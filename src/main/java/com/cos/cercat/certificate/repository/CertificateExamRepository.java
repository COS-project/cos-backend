package com.cos.cercat.certificate.repository;

import com.cos.cercat.certificate.domain.CertificateExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificateExamRepository extends JpaRepository<CertificateExam, Long> {

    @Query("""
            SELECT ce from CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE ei.examSchedule.applicationStartDateTime < :oneDayAfter
            AND ei.examSchedule.applicationStartDateTime > :today
            """)
    List<CertificateExam> findTomorrowCertificateExams(LocalDateTime oneDayAfter, LocalDateTime today);

    @Query("""
            SELECT ce FROM CertificateExam ce
            JOIN FETCH ce.examInfo ei
            WHERE ce.certificate.id = :certificateId
            ORDER BY ei.examSchedule.examDateTime DESC
            LIMIT 1
            """)
    CertificateExam findRecentCertificateExam(@Param("certificateId") Long certificateId);
}
