package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.mockExamResult.domain.UserAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("""
              SELECT ua FROM UserAnswer ua
              JOIN FETCH ua.question q
              JOIN ua.subjectResult sr
              JOIN sr.mockExamResult mr
              JOIN mr.mockExam m
              JOIN m.certificate c
              WHERE c.id = :certificateId
                AND (m.id, mr.round) IN (
                  SELECT m2.id, MAX(mr2.round)
                  FROM MockExamResult mr2
                  JOIN mr2.mockExam m2
                  WHERE mr2.user.id = :userId
                  GROUP BY m2.id
                )
                AND ua.isCorrect = false
                AND ua.user.id = :userId
              """)
    Slice<UserAnswer> getIncorrectUserAnswersByUserAndCertificate(Pageable pageable,
                                                                  @Param("userId") Long userId,
                                                                  @Param("certificateId") Long certificateId);

    @Query("""
            SELECT ua FROM UserAnswer ua
            JOIN FETCH ua.question q
            JOIN ua.subjectResult sr
            WHERE ua.isCorrect = false AND sr.mockExamResult.id = :mockExamResultId
            """)
    Slice<UserAnswer> getIncorrectUserAnswersByMockExamResult(Pageable pageable, @Param("mockExamResultId") Long mockExamResultId);

}
