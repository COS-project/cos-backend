package com.cos.cercat.domain.mockexamresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YearlyReportStrategy extends AbstractReportStrategy {

    @Autowired
    public YearlyReportStrategy(
            ScoreDataCache scoreDataCache,
            MockExamResultRepository mockExamResultRepository
    ) {
        super(scoreDataCache, mockExamResultRepository);
    }

    @Override
    public ReportType getReportType() {
        return ReportType.YEARLY;
    }
}
