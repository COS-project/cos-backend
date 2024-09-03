package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.dto.DailyScoreAverage;
import com.cos.cercat.infra.dto.MonthlyScoreAverage;
import com.cos.cercat.infra.dto.WeeklyScoreAverage;
import com.cos.cercat.domain.mockexamresult.DateCond;

import java.util.List;

public interface CustomMockExamResultRepository {

    List<DailyScoreAverage> getDailyScoreDataList(Long userId, Long certificateId, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreDataList(Long userId, Long certificateId, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreDataList(Long userId, Long certificateId, DateCond dateCond);
}
