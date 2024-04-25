package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExamResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MockExamResultJpaRepository extends JpaRepository<MockExamResultEntity, Long>, CustomMockExamResultRepository {

    @Query("""
           SELECT mer FROM MockExamResultEntity mer
           WHERE mer.mockExamEntity.id = :mockExamId
           AND mer.userEntity.id = :userId
           """)
    List<MockExamResultEntity> findByMockExamIdAndUserId(Long mockExamId, Long userId);

    @Query("""
            SELECT COUNT(mer) FROM MockExamResultEntity mer
            WHERE mer.mockExamEntity.id = :mockExamResultId
            AND mer.userEntity.id = :userId
            """)
    int countMockExamResults(Long userId, Long mockExamResultId);

    @Query("""
            SELECT COUNT(mer) FROM MockExamResultEntity mer
            WHERE mer.mockExamEntity.certificateEntity.id = :certificateId
            AND mer.userEntity.id = :userId
            AND mer.createdAt BETWEEN :goalStartDateTime AND :goalEndDateTime
            """)
    Integer countTotalMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime,
                                      LocalDateTime goalEndDateTime);

    @Query("""
            SELECT MAX(mer.totalScore)
            FROM MockExamResultEntity mer
            WHERE mer.mockExamEntity.certificateEntity.id = :certificateId
            AND mer.userEntity.id = :userId
            AND mer.createdAt BETWEEN :goalStartDateTime AND :goalEndDateTime
            """)
    Integer getMockExamResultMaxScore(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId,
                                      LocalDateTime goalStartDateTime,
                                      LocalDateTime goalEndDateTime);

    @Query("""
            SELECT COUNT(mr) FROM MockExamResultEntity mr
            JOIN mr.mockExamEntity.certificateEntity c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = CURRENT_DATE
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Integer countTodayMockExamResults(@Param("certificateId") Long certificateId,
                                      @Param("userId") Long userId);


    @Query("""
            SELECT mr from MockExamResultEntity mr
            JOIN FETCH mr.mockExamEntity me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('DATE_FORMAT', mr.createdAt, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :date, '%Y-%m-%d')
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResultEntity> findMockExamResultsByDate(@Param("certificateId") Long certificateId,
                                                         @Param("userId") Long userId,
                                                         Date date,
                                                         Pageable pageable);

    @Query("""
            SELECT mr from MockExamResultEntity mr
            JOIN FETCH mr.mockExamEntity me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('WEEK', mr.createdAt) - FUNCTION('WEEK', :firstDayOfMonth) + 1 = :weekOfMonth
            AND FUNCTION('MONTH', mr.createdAt) = :month
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResultEntity> findMockExamResultsByWeekOfMonth(@Param("certificateId") Long certificateId,
                                                                @Param("userId") Long userId,
                                                                LocalDateTime firstDayOfMonth,
                                                                int month,
                                                                int weekOfMonth,
                                                                Pageable pageable);

    @Query("""
            SELECT mr from MockExamResultEntity mr
            JOIN FETCH mr.mockExamEntity me
            LEFT JOIN me.certificateEntity c
            WHERE FUNCTION('MONTH', mr.createdAt) = :month
            AND mr.userEntity.id = :userId
            AND c.id = :certificateId
            """)
    Page<MockExamResultEntity> findMockExamResultsByMonth(@Param("certificateId") Long certificateId,
                                                          @Param("userId") Long userId,
                                                          int month,
                                                          Pageable pageable);

}
