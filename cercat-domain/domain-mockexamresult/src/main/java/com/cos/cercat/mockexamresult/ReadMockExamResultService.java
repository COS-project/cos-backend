package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.learning.Goal;
import com.cos.cercat.learning.GoalReader;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.PermissionValidator;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
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

    public List<MockExamResultDetail> getMockExamResultDetail(TargetMockExam targetMockExam,
                                                              TargetUser targetUser) {
        User user = userReader.read(targetUser);
        MockExam mockExam = mockExamReader.read(targetMockExam);
        return mockExamResultFinder.findDetails(user, mockExam);
    }

    public SliceResult<UserAnswer> getAllWrongUserAnswers(TargetCertificate targetCertificate,
                                                          TargetUser targetUser,
                                                          Cursor cursor) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        return userAnswerManager.findAllWrongUserAnswers(
                user,
                certificate,
                cursor
        );

    }

    public SliceResult<UserAnswer> getWrongUserAnswers(TargetMockExamResult targetMockExamResult,
                                                       TargetUser targetUser,
                                                       Cursor cursor) {
        User user = userReader.read(targetUser);
        MockExamResult mockExamResult = mockExamResultReader.read(targetMockExamResult);
        permissionValidator.validate(mockExamResult, user);
        return userAnswerManager.findWrongUserAnswers(mockExamResult, cursor);
    }

    public PageResult<MockExamResult> findMockExamResults(TargetUser targetUser,
                                                          TargetCertificate targetCertificate,
                                                          DateType dateType,
                                                          DateCond dateCond,
                                                          Cursor cursor) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        return mockExamResultFinder.findMockExamResults(
                user,
                certificate,
                dateType,
                dateCond,
                cursor
        );
    }

    public List<SubjectResultStatistics> getSubjectResultsStatistics(TargetUser targetUser,
                                                                     TargetCertificate targetCertificate) {

        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);

        Goal goal = goalReader.readRecentGoal(user, certificate);
        return subjectResultReader.readStatistics(
                user,
                certificate,
                goal.getGoalPeriod()
        );
    }

    public List<ScoreData> findReportData(TargetUser targetUser,
                                             TargetCertificate targetCertificate,
                                             ReportType reportType,
                                             DateCond dateCond) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        return mockExamResultFinder.findReportData(
                user,
                certificate,
                reportType,
                dateCond
        );
    }

    public MockExamResult getRecentMockExamResult(TargetMockExam targetMockExam, TargetUser targetUser) {
        User user = userReader.read(targetUser);
        MockExam mockExam = mockExamReader.read(targetMockExam);
        return mockExamResultReader.readRecent(mockExam, user);
    }
}
