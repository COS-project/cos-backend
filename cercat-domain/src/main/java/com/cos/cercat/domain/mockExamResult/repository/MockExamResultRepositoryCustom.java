package com.cos.cercat.domain.mockExamResult.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.mockExamResult.dto.DailyScoreAverage;
import com.cos.cercat.domain.mockExamResult.dto.DateCond;
import com.cos.cercat.domain.mockExamResult.dto.MonthlyScoreAverage;
import com.cos.cercat.domain.mockExamResult.dto.WeeklyScoreAverage;

import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAverage> getDailyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreAverages(Certificate certificate, User user, DateCond dateCond);
}
