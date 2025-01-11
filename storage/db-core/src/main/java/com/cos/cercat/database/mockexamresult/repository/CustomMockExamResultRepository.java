package com.cos.cercat.database.mockexamresult.repository;

import com.cos.cercat.database.mockexamresult.dto.DailyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.MonthlyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.WeeklyScoreAverage;
import com.cos.cercat.domain.mockexamresult.DateCond;

import java.util.List;

public interface CustomMockExamResultRepository {

    List<DailyScoreAverage> getDailyScoreDataList(Long userId, Long certificateId, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreDataList(Long userId, Long certificateId, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreDataList(Long userId, Long certificateId, DateCond dateCond);
}
