package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.PageResult;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.GoalReader;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamReader;
import com.cos.cercat.domain.mockexam.MockExamId;
import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadMockExamResultService {

    private final MockExamResultFinder mockExamResultFinder;
    private final MockExamResultReader mockExamResultReader;
    private final UserAnswerManager userAnswerManager;
    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final PermissionValidator permissionValidator;
    private final SubjectResultReader subjectResultReader;
    private final GoalReader goalReader;
    private final MockExamReader mockExamReader;

    public List<MockExamResultDetail> getMockExamResultDetail(MockExamId mockExamId,
                                                              UserId userId) {
        User user = userReader.read(userId);
        MockExam mockExam = mockExamReader.read(mockExamId);
        return mockExamResultFinder.findDetails(user, mockExam);
    }

    public SliceResult<UserAnswer> getAllWrongUserAnswers(CertificateId certificateId,
                                                          UserId userId,
                                                          Cursor cursor) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return userAnswerManager.findAllWrongUserAnswers(
                user,
                certificate,
                cursor
        );

    }

    public SliceResult<UserAnswer> getWrongUserAnswers(MockExamResultId mockExamResultId,
                                                       UserId userId,
                                                       Cursor cursor) {
        User user = userReader.read(userId);
        MockExamResult mockExamResult = mockExamResultReader.read(mockExamResultId);
        permissionValidator.validate(mockExamResult, user);
        return userAnswerManager.findWrongUserAnswers(mockExamResult, cursor);
    }

    public PageResult<MockExamResult> findMockExamResults(UserId userId,
                                                          CertificateId certificateId,
                                                          DateType dateType,
                                                          DateCond dateCond,
                                                          Cursor cursor) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return mockExamResultFinder.findMockExamResults(
                user,
                certificate,
                dateType,
                dateCond,
                cursor
        );
    }

    public List<SubjectResultStatistics> getSubjectResultsStatistics(UserId userId,
                                                                     CertificateId certificateId) {

        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        Goal goal = goalReader.readRecentGoal(user, certificate);
        return subjectResultReader.readStatistics(
                user,
                certificate,
                goal.getGoalPeriod()
        );
    }

    public List<ScoreData> findReportData(UserId userId,
                                             CertificateId certificateId,
                                             ReportType reportType,
                                             DateCond dateCond) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return mockExamResultFinder.findReportData(
                user,
                certificate,
                reportType,
                dateCond
        );
    }

    public MockExamResult getRecentMockExamResult(MockExamId mockExamId, UserId userId) {
        User user = userReader.read(userId);
        MockExam mockExam = mockExamReader.read(mockExamId);
        return mockExamResultReader.readRecent(mockExam, user);
    }
}
