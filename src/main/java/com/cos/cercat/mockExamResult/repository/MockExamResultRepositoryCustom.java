package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAverage;
import com.cos.cercat.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAverage> getDailyReport(User user, Certificate certificate, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<WeeklyScoreAverage> getWeeklyReport(User usr, Certificate certificate);


}
