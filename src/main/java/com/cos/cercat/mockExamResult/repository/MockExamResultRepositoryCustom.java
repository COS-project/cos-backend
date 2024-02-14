package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.dto.request.DateQueryParam;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.MonthlyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAverage;
import com.cos.cercat.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAverage> getDailyReport(Certificate certificate, User user, DateQueryParam dateQueryParam);

    List<WeeklyScoreAverage> getWeeklyReport(Certificate certificate, User user, DateQueryParam dateQueryParam);

    List<MonthlyScoreAverage> getYearlyReport(Certificate certificate, User user, DateQueryParam dateQueryParam);

}
