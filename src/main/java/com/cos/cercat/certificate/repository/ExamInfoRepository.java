package com.cos.cercat.certificate.repository;

import com.cos.cercat.certificate.domain.ExamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExamInfoRepository extends JpaRepository<ExamInfo, Long> {

    @Query("""
            SELECT ei from ExamInfo ei
            JOIN FETCH ei.certificateExam
            WHERE ei.certificateExam.certificate.id = :certificateId
            ORDER BY ei.examSchedule.examDateTime ASC
            LIMIT 1
            """)
    ExamInfo findRecentExamInfo(@Param("certificateId") Long certificateId);

}
