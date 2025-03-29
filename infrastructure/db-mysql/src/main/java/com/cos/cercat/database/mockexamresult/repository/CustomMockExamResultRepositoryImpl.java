package com.cos.cercat.database.mockexamresult.repository;

import static com.cos.cercat.database.certificate.entity.QCertificateEntity.certificateEntity;
import static com.cos.cercat.database.mockexam.entity.QMockExamEntity.*;
import static com.cos.cercat.database.mockexamresult.entity.QMockExamResultEntity.mockExamResultEntity;
import static com.cos.cercat.database.user.entity.QUserEntity.userEntity;

import com.cos.cercat.database.common.util.DateUtils;
import com.cos.cercat.database.mockexam.entity.QMockExamEntity;
import com.cos.cercat.database.mockexamresult.dto.QDailyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.QMonthlyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.QWeeklyScoreAverage;
import com.cos.cercat.domain.mockexamresult.DateCond;
import com.cos.cercat.database.mockexamresult.dto.DailyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.MonthlyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.WeeklyScoreAverage;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;



@Repository
@RequiredArgsConstructor
public class CustomMockExamResultRepositoryImpl implements CustomMockExamResultRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyScoreAverage> getDailyScoreDataList(Long userId, Long certificateId,
            DateCond dateCond) {

        LocalDate firstDayOfMonth = LocalDate.of(dateCond.year(), dateCond.month(), 1);

        LocalDate sundayOfGivenWeek = DateUtils.getThisSunday(firstDayOfMonth)
                .plusWeeks(dateCond.weekOfMonth() - 1).toLocalDate();

        LocalDateTime weekStart = DateUtils.getThisSunday(sundayOfGivenWeek);
        LocalDateTime weekEnd = DateUtils.getThisSaturday(sundayOfGivenWeek);

        DateTemplate<Date> date = Expressions.dateTemplate(Date.class, "DATE({0})",
                mockExamResultEntity.createdAt);

        return queryFactory.select(
                        new QDailyScoreAverage(
                                mockExamEntity.maxScore,
                                mockExamResultEntity.totalScore.avg(),
                                mockExamResultEntity.createdAt.dayOfWeek(),
                                date
                        )
                )
                .from(mockExamResultEntity)
                .join(mockExamResultEntity.mockExamEntity.certificateEntity, certificateEntity)
                .join(mockExamResultEntity.userEntity, userEntity)
                .where(
                        certificateEntity.id.eq(certificateId),
                        userEntity.id.eq(userId),
                        betweenStartDateAndEndDate(weekStart, weekEnd)
                )
                .groupBy(date, mockExamResultEntity.createdAt.dayOfWeek(), mockExamEntity.maxScore)
                .fetch();
    }

    @Override
    public List<WeeklyScoreAverage> getWeeklyScoreDataList(Long userId, Long certificateId,
            DateCond dateCond) {

        LocalDate month = LocalDate.of(dateCond.year(), dateCond.month(), 1);

        LocalDateTime firstDayOfMonth = DateUtils.getFirstDayOfMonth(month);
        LocalDateTime lastDayOfMonth = DateUtils.getLastDayOfMonth(month);

        NumberTemplate<Integer> weekOfMonth = Expressions.numberTemplate(Integer.class,
                "FUNCTION('WEEK', {0}) - FUNCTION('WEEK', {1}) + 1", mockExamResultEntity.createdAt,
                firstDayOfMonth);

        return queryFactory.select(
                        new QWeeklyScoreAverage(
                                mockExamEntity.maxScore,
                                mockExamResultEntity.totalScore.avg(),
                                weekOfMonth))
                .from(mockExamResultEntity)
                .leftJoin(mockExamResultEntity.mockExamEntity.certificateEntity, certificateEntity)
                .leftJoin(mockExamResultEntity.userEntity, userEntity)
                .where(
                        certificateEntity.id.eq(certificateId),
                        userEntity.id.eq(userId),
                        betweenStartDateAndEndDate(firstDayOfMonth, lastDayOfMonth)
                )
                .groupBy(weekOfMonth, mockExamEntity.maxScore)
                .fetch();
    }

    @Override
    public List<MonthlyScoreAverage> getMonthlyScoreDataList(Long userId, Long certificateId,
            DateCond dateCond) {

        LocalDate currentYear = LocalDate.of(dateCond.year(), 1, 1);

        LocalDateTime firstDayOfYear = DateUtils.getFirstDayOfYear(currentYear);
        LocalDateTime lastDayOfYear = DateUtils.getLastDayOfYear(currentYear);

        return queryFactory.select(
                        new QMonthlyScoreAverage(
                                mockExamEntity.maxScore,
                                mockExamResultEntity.totalScore.avg(),
                                mockExamResultEntity.createdAt.month())
                )
                .from(mockExamResultEntity)
                .leftJoin(mockExamResultEntity.mockExamEntity.certificateEntity, certificateEntity)
                .leftJoin(mockExamResultEntity.userEntity, userEntity)
                .where(
                        certificateEntity.id.eq(certificateId),
                        userEntity.id.eq(userId),
                        betweenStartDateAndEndDate(firstDayOfYear, lastDayOfYear)
                )
                .groupBy(mockExamResultEntity.createdAt.month(), mockExamEntity.maxScore)
                .fetch();
    }

    private BooleanExpression betweenStartDateAndEndDate(LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return mockExamResultEntity.createdAt.between(startDateTime, endDateTime);
    }

}
