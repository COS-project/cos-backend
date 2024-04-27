package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamResultFinder {

    private final MockExamResultRepository mockExamResultRepository;

    public List<MockExamResultDetail> findDetails(TargetMockExam targetMockExam,
                                                  TargetUser targetUser) {
        return mockExamResultRepository.findMockExamResultDetails(targetMockExam, targetUser);
    }

    public PageResult<MockExamResult> findMockExamResults(TargetUser targetUser,
                                                          TargetCertificate targetCertificate,
                                                          DateType dateType,
                                                          DateCond dateCond,
                                                          Cursor cursor) {
        return switch (dateType) {
            case DATE ->
                    mockExamResultRepository.findMockExamResultsByDate(targetUser, targetCertificate, dateCond, cursor);
            case WEEK_OF_MONTH ->
                    mockExamResultRepository.findMockExamResultsByWeekOfMonth(targetUser, targetCertificate, dateCond, cursor);
            case MONTH ->
                    mockExamResultRepository.findMockExamResultsByMonth(targetUser, targetCertificate, dateCond, cursor);
        };
    }

    public List<ScoreData> findReportData(TargetUser targetUser,
                                         TargetCertificate targetCertificate,
                                         ReportType reportType,
                                         DateCond dateCond) {
        return switch (reportType) {
            case WEEKLY ->
                    mockExamResultRepository.getDailyScoreData(targetUser, targetCertificate, dateCond);
            case MONTHLY ->
                    mockExamResultRepository.getWeeklyScoreData(targetUser, targetCertificate, dateCond);
            case YEARLY ->
                    mockExamResultRepository.getYearlyScoreData(targetUser, targetCertificate, dateCond);
        };
    }

}
