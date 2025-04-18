package com.cos.cercat.database.certificate.repository;

import com.cos.cercat.database.certificate.entity.CertificateExamEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface CertificateExamJpaRepository extends JpaRepository<CertificateExamEntity, Long> {

    @Query("""
            SELECT ce FROM CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE ce.certificateEntity.id = :certificateId
            AND ei.examDateTime < CURRENT_TIMESTAMP
            ORDER BY ei.examDateTime DESC
            LIMIT 1
            """)
    Optional<CertificateExamEntity> findPreviousCertificateExam(Long certificateId);

    @Query("""
            SELECT ce FROM CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE ce.certificateEntity.id = :certificateId
            AND ei.examDateTime > CURRENT_TIMESTAMP
            ORDER BY ei.examDateTime ASC
            LIMIT 1
            """)
    Optional<CertificateExamEntity> findNextCertificateExam(Long certificateId);

    @Query("""
            SELECT ce from CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE FUNCTION('DATE_FORMAT', ei.applicationStartDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :applicationStartDate, '%Y-%m-%d')
            """)
    List<CertificateExamEntity> findCertificateExamsByApplicationStartDate(LocalDateTime applicationStartDate);

    @Query("""
            SELECT ce from CertificateExamEntity ce
            JOIN FETCH ce.examInfoEntity ei
            WHERE FUNCTION('DATE_FORMAT', ei.applicationDeadlineDateTime, '%Y-%m-%d')
            = FUNCTION('DATE_FORMAT', :deadlineDate, '%Y-%m-%d')
            """)
    List<CertificateExamEntity> findCertificateExamsByDeadlineDate(LocalDateTime deadlineDate);


}
