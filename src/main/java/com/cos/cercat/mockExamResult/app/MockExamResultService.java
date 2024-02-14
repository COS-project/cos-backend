package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAverage;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExamResult.repository.MockExamResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

    public int getMockExamResultsCount(MockExam mockExam, User user) {
        return mockExamResultRepository.countMockExamResultsByMockExamAndUser(mockExam, user);
    }

    public MockExamResult getMockExamResult(Long mockExamResultId) {
        return mockExamResultRepository.findById(mockExamResultId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND));
    }

    public int countMockExamResults(Certificate certificate, User user) {
        Integer countMockExamResults = mockExamResultRepository.countMockExamResultsByMockExam_CertificateAndUser(certificate, user);

        return Objects.requireNonNullElse(countMockExamResults, 0);
    }

    public int getCurrentMaxScore(Certificate certificate, User user) {
        Integer mockExamResultMaxScore = mockExamResultRepository.getMockExamResultMaxScore(certificate.getId(), user.getId());

        return Objects.requireNonNullElse(mockExamResultMaxScore, 0);
    }

    public int countTodayMockExamResults(Certificate certificate, User user) {
        Integer countTodayMockExamResults = mockExamResultRepository.countTodayMockExamResults(certificate.getId(), user.getId());

        return Objects.requireNonNullElse(countTodayMockExamResults, 0);
    }

    public List<MockExamResult> getMockExamResultsByDate(Certificate certificate, User user, Date date) {
        return mockExamResultRepository.findMockExamResultsByDate(certificate.getId(), user.getId(), date);
    }

    public List<DailyScoreAverage> getWeeklyReport(Certificate certificate, User user) {
        LocalDateTime thisMonday = getThisMonday(LocalDateTime.now());
        LocalDateTime thisSunday = getThisSunday(LocalDateTime.now());

        return mockExamResultRepository.getDailyReport(certificate, user, thisMonday, thisSunday);
    }

    public List<WeeklyScoreAverage> getMonthlyReport(Certificate certificate, User user) {
        LocalDateTime firstDayOfMonth = getFirstDayOfMonth(LocalDateTime.now());
        LocalDateTime lastDayOfMonth = getLastDayOfMonth(LocalDateTime.now());

        return mockExamResultRepository.getWeeklyReport(certificate, user, firstDayOfMonth, lastDayOfMonth).stream()
                .sorted(Comparator.comparing(WeeklyScoreAverage::getWeekOfMonth))
                .toList();
    }

    private LocalDateTime getThisSunday(LocalDateTime now) {
        return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().plusDays(1).atStartOfDay();
    }

    private LocalDateTime getThisMonday(LocalDateTime now) {
        return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
    }

    private LocalDateTime getLastDayOfMonth(LocalDateTime now) {
        return now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().plusDays(1).atStartOfDay();
    }

    private LocalDateTime getFirstDayOfMonth(LocalDateTime now) {
        return now.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
    }


}
