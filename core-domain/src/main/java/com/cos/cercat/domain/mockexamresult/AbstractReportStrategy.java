package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

public abstract class AbstractReportStrategy implements ReportDataStrategy {
    protected final ScoreDataCache scoreDataCache;
    protected final MockExamResultRepository mockExamResultRepository;

    protected AbstractReportStrategy(ScoreDataCache scoreDataCache, MockExamResultRepository mockExamResultRepository) {
        this.scoreDataCache = scoreDataCache;
        this.mockExamResultRepository = mockExamResultRepository;
    }

    @Override
    public ScoreDataList getScoreData(User user, Certificate certificate, DateCond dateCond) {
        return scoreDataCache.get(getReportType(), user, certificate)
                .orElseGet(() -> {
                    ScoreDataList scoreDataList = mockExamResultRepository.getScoreData(
                            getReportType(),
                            user,
                            certificate,
                            dateCond
                    );

                    if (scoreDataList.dataList().isEmpty()) {
                        return scoreDataList;
                    }

                    scoreDataCache.cache(getReportType(), user, certificate, scoreDataList);
                    return scoreDataList;
                });
    }

    protected abstract ReportType getReportType();
}
