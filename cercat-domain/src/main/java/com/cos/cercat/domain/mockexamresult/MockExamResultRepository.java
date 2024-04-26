package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface MockExamResultRepository {

    TargetMockExamResult save(TargetUser targetUser,
                              TargetMockExam targetMockExam,
                              NewMockExamResult newMockExamResult);

    int countMockExamResult(TargetUser targetUser,
                            TargetMockExam targetMockExam);

    UserAnswer findUserAnswer(TargetUserAnswer targetUserAnswer);

    void update(UserAnswer userAnswer);

    List<MockExamResultDetail> findMockExamResultDetails(TargetMockExam targetMockExam,
                                                         TargetUser targetUser);

    SliceResult<UserAnswer> findAllWrongUserAnswers(TargetCertificate targetCertificate,
                                                    TargetUser targetUser, Cursor cursor);

    SliceResult<UserAnswer> findWrongUserAnswers(TargetMockExamResult targetMockExamResult,
                                                 TargetUser targetUser, Cursor cursor);

    MockExamResult find(TargetMockExamResult targetMockExamResult);

    PageResult<MockExamResult> findMockExamResultsByDate(TargetUser targetUser, TargetCertificate targetCertificate, DateCond dateCond, Cursor cursor);

    PageResult<MockExamResult> findMockExamResultsByWeekOfMonth(TargetUser targetUser, TargetCertificate targetCertificate, DateCond dateCond, Cursor cursor);

    PageResult<MockExamResult> findMockExamResultsByMonth(TargetUser targetUser, TargetCertificate targetCertificate, DateCond dateCond, Cursor cursor);

    List<ScoreData> getDailyScoreData(TargetUser targetUser,
                                      TargetCertificate targetCertificate,
                                      DateCond dateCond);

    List<ScoreData> getWeeklyScoreData(TargetUser targetUser,
                                       TargetCertificate targetCertificate,
                                       DateCond dateCond);

    List<ScoreData> getYearlyScoreData(TargetUser targetUser,
                                       TargetCertificate targetCertificate,
                                       DateCond dateCond);

    List<SubjectResultStatistics> getSubjectResultStatistics(User user,
                                                             Certificate certificate,
                                                             GoalPeriod goalDate);

    int getCurrentMaxScore(TargetCertificate targetCertificate,
                           TargetUser targetUser,
                           GoalPeriod goalPeriod);

    int countTodayMockExamResults(TargetCertificate targetCertificate, TargetUser targetUser);

    int countTotalMockExamResults(TargetCertificate targetCertificate,
                                  TargetUser targetUser,
                                  GoalPeriod goalPeriod);

    MockExamResult readRecent(MockExam mockExam, User user);
}
