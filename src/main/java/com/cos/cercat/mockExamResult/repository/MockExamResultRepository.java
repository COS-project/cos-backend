package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long> {

    List<MockExamResult> findMockExamResultByMockExamAndUser(MockExam mockExam, User user);

    int countMockExamResultsByMockExamAndUser(MockExam mockExam, User user);

    Integer countMockExamResultsByMockExam_CertificateAndUser(Certificate certificate, User user);

    @Query("""
            SELECT MAX(totalScoreQuery.totalScore)
            FROM (
             SELECT SUM(sr.score) AS totalScore
             FROM SubjectResult sr
             JOIN sr.mockExamResult.user u
             JOIN sr.subject.certificate c
             WHERE u.id = :userId
             AND c.id = :certificateId
             GROUP BY sr.mockExamResult.id) totalScoreQuery
            """)
    Integer getMockExamResultMaxScore(@Param("certificateId") Long certificateId,
                                  @Param("userId") Long userId);

    @Query("""
            SELECT COUNT(mr) FROM MockExamResult mr
            JOIN mr.mockExam.certificate c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = CURRENT_DATE
            AND mr.user.id = :userId
            AND c.id = :certificateId
            """)
    public Integer countTodayMockExamResults(@Param("certificateId") Long certificateId,
                                         @Param("userId") Long userId);

}
