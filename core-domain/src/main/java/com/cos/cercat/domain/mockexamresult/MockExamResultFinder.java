package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.PageResult;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamResultFinder {

    private final MockExamResultRepository mockExamResultRepository;

    public List<MockExamResultDetail> findDetails(User user,
                                                  MockExam mockExam) {
        return mockExamResultRepository.findMockExamResultDetails(user, mockExam);
    }

    public PageResult<MockExamResult> findMockExamResults(User user,
                                                          Certificate certificate,
                                                          DateType dateType,
                                                          DateCond dateCond,
                                                          Cursor cursor) {
        return switch (dateType) {
            case DATE ->
                    mockExamResultRepository.findMockExamResultsByDate(user, certificate, dateCond, cursor);
            case WEEK_OF_MONTH ->
                    mockExamResultRepository.findMockExamResultsByWeekOfMonth(user, certificate, dateCond, cursor);
            case MONTH ->
                    mockExamResultRepository.findMockExamResultsByMonth(user, certificate, dateCond, cursor);
        };
    }

    public List<ScoreData> findReportData(User user,
                                          Certificate certificate,
                                          ReportType reportType,
                                          DateCond dateCond) {
        return switch (reportType) {
            case WEEKLY ->
                    mockExamResultRepository.getDailyScoreData(user, certificate, dateCond);
            case MONTHLY ->
                    mockExamResultRepository.getWeeklyScoreData(user, certificate, dateCond);
            case YEARLY ->
                    mockExamResultRepository.getYearlyScoreData(user, certificate, dateCond);
        };
    }

}
