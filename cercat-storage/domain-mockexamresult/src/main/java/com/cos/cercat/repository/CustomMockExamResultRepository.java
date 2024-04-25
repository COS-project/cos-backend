package com.cos.cercat.repository;

import com.cos.cercat.dto.DailyScoreAverage;
import com.cos.cercat.domain.mockexamresult.DateCond;
import com.cos.cercat.dto.MonthlyScoreAverage;
import com.cos.cercat.dto.WeeklyScoreAverage;

import java.util.List;

public interface CustomMockExamResultRepository {

    List<DailyScoreAverage> getDailyScoreDataList(Long certificateId, Long userId, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreDataList(Long certificateId, Long userId, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreDataList(Long certificateId, Long userId, DateCond dateCond);
}
