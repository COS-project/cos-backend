package com.cos.cercat.domain.mockexamresult;

import org.springframework.stereotype.Component;

@Component
public class WeeklyReportStrategy extends AbstractReportStrategy {

    protected WeeklyReportStrategy(ScoreDataCache scoreDataCache,
            MockExamResultRepository mockExamResultRepository) {
        super(scoreDataCache, mockExamResultRepository);
    }

    @Override
    protected ReportType getReportType() {
        return ReportType.WEEKLY;
    }
}
