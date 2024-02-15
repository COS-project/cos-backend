package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.mockExamResult.domain.SubjectResult;
import com.cos.cercat.mockExamResult.dto.SubjectResultsAVG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubjectResultRepository extends JpaRepository<SubjectResult, Long> {

    @Query("""
            SELECT new com.cos.cercat.mockExamResult.dto.SubjectResultsAVG(sr.subject, AVG(sr.correctRate), AVG(sr.totalTakenTime))
            FROM SubjectResult sr
            JOIN sr.subject.certificate c
            JOIN sr.mockExamResult.user u
            WHERE c.id = :certificateId AND u.id = :userId
            AND :goalStartDateTime < sr.mockExamResult.createdAt
            GROUP BY sr.subject
            """)
    List<SubjectResultsAVG> getSubjectResultsAVG(@Param("certificateId") Long certificateId,
                                                 @Param("userId") Long userId,
                                                 LocalDateTime goalStartDateTime);

}
