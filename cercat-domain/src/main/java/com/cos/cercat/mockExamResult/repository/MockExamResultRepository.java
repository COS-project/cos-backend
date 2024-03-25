package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long>, MockExamResultRepositoryCustom, MockExamResultBatchRepository {

    List<MockExamResult> findMockExamResultByMockExamAndUser(MockExam mockExam, User user);

    int countMockExamResultsByMockExamAndUser(MockExam mockExam, User user);

    @Query("""
            SELECT COUNT(mer) FROM MockExamResult mer
            WHERE mer.mockExam.certificate.id = :certificateId
            AND mer.user.id = :userId
            AND :goalStartDateTime < mer.createdAt
            """)
    Integer countTotalMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime);

    @Query("""
            SELECT MAX(mer.totalScore)
            FROM MockExamResult mer
            WHERE mer.mockExam.certificate.id = :certificateId
            AND mer.user.id = :userId
            AND :goalStartDateTime < mer.createdAt
            """)
    Integer getMockExamResultMaxScore(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime);

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
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificate c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :date, '%Y-%m-%d')
            AND mr.user.id = :userId
            AND c.id = :certificateId
            """)
    Slice<MockExamResult> findMockExamResultsByDate(@Param("certificateId") Long certificateId,
                                                    @Param("userId") Long userId,
                                                    Date date,
                                                    Pageable pageable);

    @Query("""
            SELECT mr from MockExamResult mr
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificate c
            WHERE FUNCTION('WEEK', mr.createdAt) - FUNCTION('WEEK', :firstDayOfMonth) + 1 = :weekOfDay
            AND FUNCTION('MONTH', mr.createdAt) = FUNCTION('MONTH', CURRENT_DATE)
            AND mr.user.id = :userId
            AND c.id = :certificateId
            """)
    Slice<MockExamResult> findMockExamResultsByWeekOfMonth(@Param("certificateId") Long certificateId,
                                                           @Param("userId") Long userId,
                                                           LocalDateTime firstDayOfMonth,
                                                           int weekOfDay,
                                                           Pageable pageable);

    @Query("""
            SELECT mr from MockExamResult mr
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificate c
            WHERE FUNCTION('MONTH', mr.createdAt) = :month
            AND mr.user.id = :userId
            AND c.id = :certificateId
            """)
    Slice<MockExamResult> findMockExamResultsByMonth(@Param("certificateId") Long certificateId,
                                                    @Param("userId") Long userId,
                                                    int month,
                                                    Pageable pageable);

}
