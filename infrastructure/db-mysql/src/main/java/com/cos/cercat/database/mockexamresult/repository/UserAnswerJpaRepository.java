package com.cos.cercat.database.mockexamresult.repository;

import com.cos.cercat.database.mockexamresult.entity.UserAnswerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnswerJpaRepository extends JpaRepository<UserAnswerEntity, Long> {

    @Query("""
              SELECT ua FROM UserAnswerEntity ua
              JOIN FETCH ua.questionEntity q
              JOIN ua.subjectResultEntity sr
              JOIN sr.mockExamResultEntity mr
              JOIN mr.mockExamEntity m
              JOIN m.certificateEntity c
              WHERE c.id = :certificateId
                AND (m.id, mr.round) IN (
                  SELECT m2.id, MAX(mr2.round)
                  FROM MockExamResultEntity mr2
                  JOIN mr2.mockExamEntity m2
                  WHERE mr2.userEntity.id = :userId
                  GROUP BY m2.id
                )
                AND ua.isCorrect = false
                AND ua.isReviewed = false
                AND ua.userEntity.id = :userId
              """)
    Slice<UserAnswerEntity> findWrongUserAnswersByUserIdAndCertificateId(@Param("userId") Long userId,
                                                                         @Param("certificateId") Long certificateId,
                                                                         Pageable pageable);

    @Query("""
            SELECT ua FROM UserAnswerEntity ua
            JOIN FETCH ua.questionEntity q
            JOIN ua.subjectResultEntity sr
            WHERE ua.isCorrect = false
            AND ua.isReviewed = false
            AND sr.mockExamResultEntity.id = :mockExamResultId
            """)
    Slice<UserAnswerEntity> findWrongUserAnswersByMockExamResultId(@Param("mockExamResultId") Long mockExamResultId,
                                                                   Pageable pageable);

    @Modifying
    @Query("""
            UPDATE UserAnswerEntity ua
            SET ua.isReviewed = true
            WHERE ua.id = :userAnswerId
           """)
    void updateForReview(Long userAnswerId);


}
