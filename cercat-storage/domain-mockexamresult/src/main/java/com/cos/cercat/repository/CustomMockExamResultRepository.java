package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.dto.DailyScoreAverage;
import com.cos.cercat.dto.DateCond;
import com.cos.cercat.dto.MonthlyScoreAverage;
import com.cos.cercat.dto.WeeklyScoreAverage;

import java.util.List;

public interface CustomMockExamResultRepository {

    List<DailyScoreAverage> getDailyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond);

    List<WeeklyScoreAverage> getWeeklyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond);

    List<MonthlyScoreAverage> getMonthlyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond);
}
