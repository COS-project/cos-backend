package com.cos.cercat.repository;

import com.cos.cercat.domain.SubjectResultEntity;
import com.cos.cercat.dto.SubjectResultsAVG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubjectResultJpaRepository extends JpaRepository<SubjectResultEntity, Long> {

    @Query("""
            SELECT new com.cos.cercat.dto.SubjectResultsAVG(sr.subjectEntity, AVG(sr.correctRate), AVG(sr.totalTakenTime))
            FROM SubjectResultEntity sr
            JOIN sr.subjectEntity.certificateEntity c
            JOIN sr.mockExamResultEntity.userEntity u
            WHERE c.id = :certificateId AND u.id = :userId
            AND :goalStartDateTime < sr.mockExamResultEntity.createdAt
            GROUP BY sr.subjectEntity
            """)
    List<SubjectResultsAVG> getSubjectResultsAVG(@Param("certificateId") Long certificateId,
                                                 @Param("userId") Long userId,
                                                 LocalDateTime goalStartDateTime);

}
