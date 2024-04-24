package com.cos.cercat.repository;

import com.cos.cercat.common.util.DateUtils;
import com.cos.cercat.domain.*;
import com.cos.cercat.dto.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.cos.cercat.domain.QMockExamResultEntity.*;


@Repository
@RequiredArgsConstructor
public class CustomMockExamResultRepositoryImpl implements CustomMockExamResultRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyScoreAverage> getDailyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {

        LocalDate firstDayOfMonth = LocalDate.of(dateCond.year(), dateCond.month(), 1);

        LocalDate sundayOfGivenWeek = DateUtils.getThisSunday(firstDayOfMonth)
                .plusWeeks(dateCond.weekOfMonth() - 1).toLocalDate();

        LocalDateTime thisSunday = DateUtils.getThisSunday(sundayOfGivenWeek);
        LocalDateTime thisSaturday = DateUtils.getThisSATURDAY(sundayOfGivenWeek);

        DateTemplate<Date> date = Expressions.dateTemplate(Date.class, "DATE({0})", mockExamResultEntity.createdAt);

        return queryFactory.select(
                        new QDailyScoreAverage(
                                    mockExamResultEntity.totalScore.avg(),
                                mockExamResultEntity.createdAt.dayOfWeek(),
                                date)
                )
                .from(mockExamResultEntity)
                .leftJoin(mockExamResultEntity.mockExamEntity.certificateEntity, QCertificateEntity.certificateEntity)
                .leftJoin(mockExamResultEntity.userEntity, QUserEntity.userEntity)
                .where(
                        QCertificateEntity.certificateEntity.eq(certificateEntity),
                        QUserEntity.userEntity.eq(userEntity),
                        betweenStartDateAndEndDate(thisSunday, thisSaturday)
                )
                .groupBy(date, mockExamResultEntity.createdAt.dayOfWeek())
                .fetch();
    }

    @Override
    public List<WeeklyScoreAverage> getWeeklyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {

        LocalDate month = LocalDate.of(dateCond.year(), dateCond.month(), 1);

        LocalDateTime firstDayOfMonth = DateUtils.getFirstDayOfMonth(month);
        LocalDateTime lastDayOfMonth = DateUtils.getLastDayOfMonth(month);

        NumberTemplate<Integer> weekOfMonth = Expressions.numberTemplate(Integer.class,
                "FUNCTION('WEEK', {0}) - FUNCTION('WEEK', {1}) + 1", mockExamResultEntity.createdAt, firstDayOfMonth);

        return queryFactory.select(
                new QWeeklyScoreAverage(
                        mockExamResultEntity.totalScore.avg(),
                        weekOfMonth))
                .from(mockExamResultEntity)
                .leftJoin(mockExamResultEntity.mockExamEntity.certificateEntity, QCertificateEntity.certificateEntity)
                .leftJoin(mockExamResultEntity.userEntity, QUserEntity.userEntity)
                .where(
                        QCertificateEntity.certificateEntity.eq(certificateEntity),
                        QUserEntity.userEntity.eq(userEntity),
                        betweenStartDateAndEndDate(firstDayOfMonth, lastDayOfMonth)
                )
                .groupBy(weekOfMonth)
                .fetch();
    }

    @Override
    public List<MonthlyScoreAverage> getMonthlyScoreAverages(CertificateEntity certificateEntity, UserEntity userEntity, DateCond dateCond) {

        LocalDate year = LocalDate.of(dateCond.year(), 1, 1);

        LocalDateTime firstDayOfYear = DateUtils.getFirstDayOfYear(year);
        LocalDateTime lastDayOfYear = DateUtils.getLastDayOfYear(year);

        return queryFactory.select(
                new QMonthlyScoreAverage(
                        mockExamResultEntity.totalScore.avg(),
                        mockExamResultEntity.createdAt.month())
                )
                .from(mockExamResultEntity)
                .leftJoin(mockExamResultEntity.mockExamEntity.certificateEntity, QCertificateEntity.certificateEntity)
                .leftJoin(mockExamResultEntity.userEntity, QUserEntity.userEntity)
                .where(
                        QCertificateEntity.certificateEntity.eq(certificateEntity),
                        QUserEntity.userEntity.eq(userEntity),
                        betweenStartDateAndEndDate(firstDayOfYear, lastDayOfYear)
                )
                .groupBy(mockExamResultEntity.createdAt.month())
                .fetch();
    }

    private BooleanExpression betweenStartDateAndEndDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return mockExamResultEntity.createdAt.between(startDateTime, endDateTime);
    }

}
