package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.QCertificate;
import com.cos.cercat.mockExamResult.dto.response.DailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.QDailyScoreAverage;
import com.cos.cercat.mockExamResult.dto.response.WeeklyScoreAverage;
import com.cos.cercat.user.domain.QUser;
import com.cos.cercat.user.domain.User;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.cos.cercat.mockExamResult.domain.QMockExamResult.mockExamResult;


@Repository
@RequiredArgsConstructor
public class MockExamResultRepositoryImpl implements MockExamResultRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyScoreAverage> getDailyReport(User user, Certificate certificate, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        DateTemplate<Date> date = Expressions.dateTemplate(Date.class, "DATE({0})", mockExamResult.createdAt);

        return queryFactory.select(
                        new QDailyScoreAverage(
                                mockExamResult.totalScore.avg(),
                                mockExamResult.createdAt.dayOfWeek(),
                                date)
                )
                .from(mockExamResult)
                .leftJoin(mockExamResult.mockExam.certificate, QCertificate.certificate)
                .leftJoin(mockExamResult.user, QUser.user)
                .where(
                        QCertificate.certificate.eq(certificate),
                        QUser.user.eq(user),
                        betweenStarOfWeekAndEndOfWeek(startDateTime, endDateTime)
                )
                .groupBy(date, mockExamResult.createdAt.dayOfWeek())
                .fetch();
    }

    @Override
    public List<WeeklyScoreAverage> getWeeklyReport(User usr, Certificate certificate) {
        return null;
    }

    private BooleanExpression betweenStarOfWeekAndEndOfWeek(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return mockExamResult.createdAt.between(startDateTime, endDateTime);
    }

}
