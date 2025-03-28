package com.cos.cercat.domain.mockexamresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonthlyReportStrategy extends AbstractReportStrategy {


    @Autowired
    public MonthlyReportStrategy(ScoreDataCache scoreDataCache,
            MockExamResultRepository mockExamResultRepository) {
        super(scoreDataCache, mockExamResultRepository);
    }

    @Override
    public ReportType getReportType() {
        return ReportType.MONTHLY;
    }
}
