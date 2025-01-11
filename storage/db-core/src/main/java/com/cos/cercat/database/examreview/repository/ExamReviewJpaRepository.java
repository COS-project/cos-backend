package com.cos.cercat.database.examreview.repository;

import com.cos.cercat.database.examreview.entity.ExamReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamReviewJpaRepository extends JpaRepository<ExamReviewEntity, Long>, ExamReviewRepositoryCustom {

    @Query("""
            select exists ( select 1
            from ExamReviewEntity er
            where er.userEntity.id = :userId
            and er.certificateExamEntity.id = :certificateExamId)
            """)
    Boolean existsUserIdAndCertificateExamId(Long userId, Long certificateExamId);

}
