package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.PageResult;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.user.User;

import java.util.List;

public interface MockExamResultRepository {

    MockExamResultId save(User user,
                              MockExam mockExam,
                              NewMockExamResult newMockExamResult);

    int countMockExamResult(User user,
                            MockExam mockExam);

    UserAnswer findUserAnswer(UserAnswerId userAnswerId);

    void update(UserAnswer userAnswer);

    List<MockExamResultDetail> findMockExamResultDetails(User user,
                                                         MockExam mockExam);

    SliceResult<UserAnswer> findAllWrongUserAnswers(User user,
                                                    Certificate certificate,
                                                    Cursor cursor);

    SliceResult<UserAnswer> findWrongUserAnswers(MockExamResult mockExamResult,
                                                 Cursor cursor);

    MockExamResult find(MockExamResultId mockExamResultId);

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
