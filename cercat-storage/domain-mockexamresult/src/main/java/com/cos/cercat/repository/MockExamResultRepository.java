package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.MockExamResult;
import com.cos.cercat.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long>, MockExamResultRepositoryCustom, MockExamResultBatchRepository {

    List<MockExamResult> findMockExamResultByMockExamAndUserEntity(MockExam mockExam, UserEntity userEntity);

    int countMockExamResultsByMockExamAndUserEntity(MockExam mockExam, UserEntity userEntity);

    @Query("""
            SELECT COUNT(mer) FROM MockExamResult mer
            WHERE mer.mockExam.certificateEntity.id = :certificateId
            AND mer.userEntity.id = :userId
            AND :goalStartDateTime < mer.createdAt
            """)
    Integer countTotalMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime);

    @Query("""
            SELECT MAX(mer.totalScore)
            FROM MockExamResult mer
            WHERE mer.mockExam.certificateEntity.id = :certificateId
            AND mer.userEntity.id = :userId
            AND :goalStartDateTime < mer.createdAt
            """)
    Integer getMockExamResultMaxScore(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime);

    @Query("""
            SELECT COUNT(mr) FROM MockExamResult mr
            JOIN mr.mockExam.certificateEntity c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = CURRENT_DATE
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Integer countTodayMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId);


    @Query("""
            SELECT mr from MockExamResult mr
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :date, '%Y-%m-%d')
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResult> findMockExamResultsByDate(@Param("certificateId") Long certificateId,
                                                   @Param("userId") Long userId,
                                                   Date date,
                                                   Pageable pageable);

    @Query("""
            SELECT mr from MockExamResult mr
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('WEEK', mr.createdAt) - FUNCTION('WEEK', :firstDayOfMonth) + 1 = :weekOfMonth
            AND FUNCTION('MONTH', mr.createdAt) = :month
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResult> findMockExamResultsByWeekOfMonth(@Param("certificateId") Long certificateId,
                                                           @Param("userId") Long userId,
                                                           LocalDateTime firstDayOfMonth,
                                                           int month,
                                                           int weekOfMonth,
                                                           Pageable pageable);

    @Query("""
            SELECT mr from MockExamResult mr
            JOIN FETCH mr.mockExam me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('MONTH', mr.createdAt) = :month
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResult> findMockExamResultsByMonth(@Param("certificateId") Long certificateId,
                                                    @Param("userId") Long userId,
                                                    int month,
                                                    Pageable pageable);

}
