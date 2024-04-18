package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.MockExamResultRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.MockExamResult;
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

    private final MockExamResultRepository mockExamResultRepository;

    public List<MockExamResult> getMockExamResults(MockExam mockExam, UserEntity userEntity) {

        return mockExamResultRepository.findMockExamResultByMockExamAndUserEntity(mockExam, userEntity).stream()
                .sorted(Comparator.comparing(MockExamResult::getCreatedAt).reversed())
                .toList();
    }

    public MockExamResult save(MockExamResult mockExamResult) {
        return mockExamResultRepository.save(mockExamResult);
    }

    public long batchInsert(MockExamResult mockExamResult) {

        return mockExamResultRepository.batchInsert(mockExamResult);
    }

    public int getMockExamResultsCount(MockExam mockExam, UserEntity userEntity) {
        return mockExamResultRepository.countMockExamResultsByMockExamAndUserEntity(mockExam, userEntity);
    }

    public MockExamResult getMockExamResult(Long mockExamResultId) {
        return mockExamResultRepository.findById(mockExamResultId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND));
    }

    public int countTotalMockExamResults(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        Integer countMockExamResults = mockExamResultRepository.countTotalMockExamResults(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(countMockExamResults, 0);
    }

    public int getCurrentMaxScore(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        Integer mockExamResultMaxScore = mockExamResultRepository.getMockExamResultMaxScore(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(mockExamResultMaxScore, 0);
    }

    public int countTodayMockExamResults(CertificateEntity certificateEntity, UserEntity userEntity) {
        Integer countTodayMockExamResults = mockExamResultRepository.countTodayMockExamResults(certificateEntity.getId(), userEntity.getId());

        return Objects.requireNonNullElse(countTodayMockExamResults, 0);
    }

    public List<DailyScoreAverage> getWeeklyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultRepository.getDailyScoreAverages(certificateEntity, userEntity, dateCond);
    }

    public List<WeeklyScoreAverage> getMonthlyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultRepository.getWeeklyScoreAverages(certificateEntity, userEntity, dateCond).stream()
                .sorted(Comparator.comparing(WeeklyScoreAverage::getWeekOfMonth))
                .toList();
    }

    public List<MonthlyScoreAverage> getYearlyReport(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {
        return mockExamResultRepository.getMonthlyScoreAverages(certificateEntity, userEntity, dateCond).stream()
                .sorted(Comparator.comparing(MonthlyScoreAverage::getMonth))
                .toList();
    }

    public Page<MockExamResult> getMockExamResultsByDate(CertificateEntity certificateEntity,
                                                         UserEntity userEntity,
                                                         Date date,
                                                         Pageable pageable) {
        return mockExamResultRepository.findMockExamResultsByDate(
                certificateEntity.getId(),
                userEntity.getId(),
                date,
                pageable
        );
    }

    public Page<MockExamResult> getMockExamResultsByWeekOfMonth(CertificateEntity certificateEntity,
                                                                UserEntity userEntity,
                                                                DateCond dateCond,
                                                                Pageable pageable) {

        return mockExamResultRepository.findMockExamResultsByWeekOfMonth(
                certificateEntity.getId(),
                userEntity.getId(),
                DateUtils.getFirstDayOfMonth(LocalDate.of(dateCond.year(), dateCond.month(), 1)),
                dateCond.month(),
                dateCond.weekOfMonth(),
                pageable
        );
    }

    public Page<MockExamResult> getMockExamResultsByMonth(CertificateEntity certificateEntity,
                                                          UserEntity userEntity,
                                                          int month,
                                                          Pageable pageable) {
        return mockExamResultRepository.findMockExamResultsByMonth(
                certificateEntity.getId(),
                userEntity.getId(),
                month,
                pageable
        );
    }
}
