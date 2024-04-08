package com.cos.cercat.service;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

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

    public List<MockExamResult> getMockExamResults(MockExam mockExam, User user) {

        return mockExamResultRepository.findMockExamResultByMockExamAndUser(mockExam, user).stream()
                .sorted(Comparator.comparing(MockExamResult::getCreatedAt).reversed())
                .toList();
    }

    public MockExamResult save(MockExamResult mockExamResult) {
        return mockExamResultRepository.save(mockExamResult);
    }

    public long batchInsert(MockExamResult mockExamResult) {

        return mockExamResultRepository.batchInsert(mockExamResult);
    }

    public int getMockExamResultsCount(MockExam mockExam, User user) {
        return mockExamResultRepository.countMockExamResultsByMockExamAndUser(mockExam, user);
    }

    public MockExamResult getMockExamResult(Long mockExamResultId) {
        return mockExamResultRepository.findById(mockExamResultId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND));
    }

    public int countTotalMockExamResults(Certificate certificate, User user, LocalDateTime goalStartDateTime) {
        Integer countMockExamResults = mockExamResultRepository.countTotalMockExamResults(certificate.getId(), user.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(countMockExamResults, 0);
    }

    public int getCurrentMaxScore(Certificate certificate, User user, LocalDateTime goalStartDateTime) {
        Integer mockExamResultMaxScore = mockExamResultRepository.getMockExamResultMaxScore(certificate.getId(), user.getId(), goalStartDateTime);

        return Objects.requireNonNullElse(mockExamResultMaxScore, 0);
    }

    public int countTodayMockExamResults(Certificate certificate, User user) {
        Integer countTodayMockExamResults = mockExamResultRepository.countTodayMockExamResults(certificate.getId(), user.getId());

        return Objects.requireNonNullElse(countTodayMockExamResults, 0);
    }

    public List<DailyScoreAverage> getWeeklyReport(Certificate certificate, User user, DateCond dateCond) {
        return mockExamResultRepository.getDailyScoreAverages(certificate, user, dateCond);
    }

    public List<WeeklyScoreAverage> getMonthlyReport(Certificate certificate, User user, DateCond dateCond) {
        return mockExamResultRepository.getWeeklyScoreAverages(certificate, user, dateCond).stream()
                .sorted(Comparator.comparing(WeeklyScoreAverage::getWeekOfMonth))
                .toList();
    }

    public List<MonthlyScoreAverage> getYearlyReport(Certificate certificate, User user, DateCond dateCond) {
        return mockExamResultRepository.getMonthlyScoreAverages(certificate, user, dateCond).stream()
                .sorted(Comparator.comparing(MonthlyScoreAverage::getMonth))
                .toList();
    }

    public Slice<MockExamResult> getMockExamResultsByDate(Certificate certificate,
                                                          User user,
                                                          Date date,
                                                          Pageable pageable) {
        return mockExamResultRepository.findMockExamResultsByDate(
                certificate.getId(),
                user.getId(),
                date,
                pageable
        );
    }

    public Slice<MockExamResult> getMockExamResultsByWeekOfMonth(Certificate certificate,
                                                                 User user,
                                                                 int month,
                                                                 int weekOfMonth,
                                                                 Pageable pageable) {

        return mockExamResultRepository.findMockExamResultsByWeekOfMonth(
                certificate.getId(),
                user.getId(),
                DateUtils.getFirstDayOfMonth(LocalDateTime.now().toLocalDate()),
                month,
                weekOfMonth,
                pageable
        );
    }

    public Slice<MockExamResult> getMockExamResultsByMonth(Certificate certificate,
                                                           User user,
                                                           int month,
                                                           Pageable pageable) {
        return mockExamResultRepository.findMockExamResultsByMonth(
                certificate.getId(),
                user.getId(),
                month,
                pageable
        );
    }
}
