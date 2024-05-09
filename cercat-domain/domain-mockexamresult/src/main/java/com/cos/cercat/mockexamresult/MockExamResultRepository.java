package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.List;

public interface MockExamResultRepository {

    TargetMockExamResult save(User user,
                              MockExam mockExam,
                              NewMockExamResult newMockExamResult);

    int countMockExamResult(User user,
                            MockExam mockExam);

    UserAnswer findUserAnswer(TargetUserAnswer targetUserAnswer);

    void update(UserAnswer userAnswer);

    List<MockExamResultDetail> findMockExamResultDetails(User user,
                                                         MockExam mockExam);

    SliceResult<UserAnswer> findAllWrongUserAnswers(User user,
                                                    Certificate certificate,
                                                    Cursor cursor);

    SliceResult<UserAnswer> findWrongUserAnswers(MockExamResult mockExamResult,
                                                 Cursor cursor);

    MockExamResult find(TargetMockExamResult targetMockExamResult);

    PageResult<MockExamResult> findMockExamResultsByDate(User user,
                                                         Certificate certificate,
                                                         DateCond dateCond,
                                                         Cursor cursor);

    PageResult<MockExamResult> findMockExamResultsByWeekOfMonth(User user,
                                                                Certificate certificate,
                                                                DateCond dateCond,
                                                                Cursor cursor);

    PageResult<MockExamResult> findMockExamResultsByMonth(User user,
                                                          Certificate certificate,
                                                          DateCond dateCond,
                                                          Cursor cursor);

    List<ScoreData> getDailyScoreData(User user,
                                      Certificate certificate,
                                      DateCond dateCond);

    List<ScoreData> getWeeklyScoreData(User user,
                                       Certificate certificate,
                                       DateCond dateCond);

    List<ScoreData> getYearlyScoreData(User user,
                                       Certificate certificate,
                                       DateCond dateCond);

    List<SubjectResultStatistics> getSubjectResultStatistics(User user,
                                                             Certificate certificate,
                                                             GoalPeriod goalDate);

    int getCurrentMaxScore(User user,
                           Certificate certificate,
                           GoalPeriod goalPeriod);

    int countTodayMockExamResults(User user,
                                  Certificate certificate);

    int countTotalMockExamResults(User user,
                                  Certificate certificate,
                                  GoalPeriod goalPeriod);

    MockExamResult readRecent(MockExam mockExam, User user);
}
