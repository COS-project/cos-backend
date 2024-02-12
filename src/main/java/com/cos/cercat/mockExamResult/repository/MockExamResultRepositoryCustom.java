package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAVG;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAVG;
import com.cos.cercat.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MockExamResultRepositoryCustom {

    List<DailyScoreAVG> getDailyReport(User user, Certificate certificate, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<WeeklyScoreAVG> getWeeklyReport(User usr, Certificate certificate);


}
