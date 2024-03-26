package com.cos.cercat.repository;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
import com.cos.cercat.dto.DailyScoreAverage;
import com.cos.cercat.dto.DateCond;
import com.cos.cercat.dto.MonthlyScoreAverage;
import com.cos.cercat.dto.WeeklyScoreAverage;

import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAverage> getDailyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreAverages(Certificate certificate, User user, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreAverages(Certificate certificate, User user, DateCond dateCond);
}
