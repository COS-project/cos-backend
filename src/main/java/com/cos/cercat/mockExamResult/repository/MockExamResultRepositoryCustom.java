package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.dto.request.DateCond;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.MonthlyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAverage;
import com.cos.cercat.user.domain.User;

import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAverage> getDailyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreAverages(Certificate certificate, User user, DateCond dateCond);
}
