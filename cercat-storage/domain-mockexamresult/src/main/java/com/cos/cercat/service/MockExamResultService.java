package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.MockExamResultEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.MockExamResultJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.dto.DateCond;
import com.cos.cercat.dto.DailyScoreAverage;
import com.cos.cercat.dto.MonthlyScoreAverage;
import com.cos.cercat.dto.WeeklyScoreAverage;
import com.cos.cercat.common.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamResultService {

    private final MockExamResultJpaRepository mockExamResultJpaRepository;

    public List<MockExamResultEntity> getMockExamResults(MockExamEntity mockExamEntity, UserEntity userEntity) {

        return mockExamResultJpaRepository.findMockExamResultByMockExamEntityAndUserEntity(mockExamEntity, userEntity).stream()
                .sorted(Comparator.comparing(MockExamResultEntity::getCreatedAt).reversed())
                .toList();
    }

    public MockExamResultEntity save(MockExamResultEntity mockExamResultEntity) {
        return mockExamResultJpaRepository.save(mockExamResultEntity);
    }

//    public long batchInsert(MockExamResultEntity mockExamResultEntity) {
//
//        return mockExamResultJpaRepository.batchInsert(mockExamResultEntity);
//    }

//    public int getMockExamResultsCount(MockExamEntity mockExamEntity, UserEntity userEntity) {
//        return mockExamResultJpaRepository.countMockExamResults(mockExamEntity, userEntity);
//    }

    public MockExamResultEntity getMockExamResult(Long mockExamResultId) {
        return mockExamResultJpaRepository.findById(mockExamResultId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND));
    }

    public int countTotalMockExamResults(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        Integer countMockExamResults = mockExamResultJpaRepository.countTotalMockExamResults(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(countMockExamResults, 0);
    }

    public int getCurrentMaxScore(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        Integer mockExamResultMaxScore = mockExamResultJpaRepository.getMockExamResultMaxScore(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(mockExamResultMaxScore, 0);
    }

    public int countTodayMockExamResults(CertificateEntity certificateEntity, UserEntity userEntity) {
        Integer countTodayMockExamResults = mockExamResultJpaRepository.countTodayMockExamResults(certificateEntity.getId(), userEntity.getId());

        return Objects.requireNonNullElse(countTodayMockExamResults, 0);
    }

    public List<DailyScoreAverage> getWeeklyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultJpaRepository.getDailyScoreAverages(certificateEntity, userEntity, dateCond);
    }

    public List<WeeklyScoreAverage> getMonthlyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultJpaRepository.getWeeklyScoreAverages(certificateEntity, userEntity, dateCond).stream()
                .sorted(Comparator.comparing(WeeklyScoreAverage::getWeekOfMonth))
                .toList();
    }

    public List<MonthlyScoreAverage> getYearlyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultJpaRepository.getMonthlyScoreAverages(certificateEntity, userEntity, dateCond).stream()
                .sorted(Comparator.comparing(MonthlyScoreAverage::getMonth))
                .toList();
    }

    public Page<MockExamResultEntity> getMockExamResultsByDate(CertificateEntity certificateEntity,
                                                               UserEntity userEntity,
                                                               Date date,
                                                               Pageable pageable) {
        return mockExamResultJpaRepository.findMockExamResultsByDate(
                certificateEntity.getId(),
                userEntity.getId(),
                date,
                pageable
        );
    }

    public Page<MockExamResultEntity> getMockExamResultsByWeekOfMonth(CertificateEntity certificateEntity,
                                                                      UserEntity userEntity,
                                                                      DateCond dateCond,
                                                                      Pageable pageable) {

        return mockExamResultJpaRepository.findMockExamResultsByWeekOfMonth(
                certificateEntity.getId(),
                userEntity.getId(),
                DateUtils.getFirstDayOfMonth(LocalDate.of(dateCond.year(), dateCond.month(), 1)),
                dateCond.month(),
                dateCond.weekOfMonth(),
                pageable
        );
    }

    public Page<MockExamResultEntity> getMockExamResultsByMonth(CertificateEntity certificateEntity,
                                                                UserEntity userEntity,
                                                                int month,
                                                                Pageable pageable) {
        return mockExamResultJpaRepository.findMockExamResultsByMonth(
                certificateEntity.getId(),
                userEntity.getId(),
                month,
                pageable
        );
    }
}
