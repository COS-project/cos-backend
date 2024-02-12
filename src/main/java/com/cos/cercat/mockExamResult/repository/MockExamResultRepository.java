package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long>, MockExamResultRepositoryCustom {

    List<MockExamResult> findMockExamResultByMockExamAndUser(MockExam mockExam, User user);

    int countMockExamResultsByMockExamAndUser(MockExam mockExam, User user);

    Integer countMockExamResultsByMockExam_CertificateAndUser(Certificate certificate, User user);

    @Query("""
            SELECT MAX(mr.totalScore)
            FROM MockExamResult mr
            WHERE mr.mockExam.certificate.id = :certificateId
            AND mr.user.id = :userId
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
    Integer countTodayMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId);


    @Query("""
            SELECT mr from MockExamResult mr
            JOIN mr.mockExam.certificate c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :date, '%Y-%m-%d')
            AND mr.user.id = :userId
            AND c.id = :certificateId
            """)
    List<MockExamResult> findMockExamResultsByDate(@Param("certificateId") Long certificateId,
                                                   @Param("userId") Long userId,
                                                   Date date);

}
