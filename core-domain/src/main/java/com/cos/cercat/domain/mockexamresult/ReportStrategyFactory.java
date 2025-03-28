package com.cos.cercat.domain.mockexamresult;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportStrategyFactory {
    private final Map<ReportType, ReportDataStrategy> strategies;

    @Autowired
    public ReportStrategyFactory(List<ReportDataStrategy> strategyList) {
        strategies = new EnumMap<>(ReportType.class);
        strategies.put(ReportType.WEEKLY, getStrategyByType(strategyList, WeeklyReportStrategy.class));
        strategies.put(ReportType.MONTHLY, getStrategyByType(strategyList, MonthlyReportStrategy.class));
        strategies.put(ReportType.YEARLY, getStrategyByType(strategyList, YearlyReportStrategy.class));
    }

    private <T extends ReportDataStrategy> T getStrategyByType(List<ReportDataStrategy> strategies, Class<T> strategyClass) {
        return strategyClass.cast(strategies.stream()
                .filter(strategyClass::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("전략을 찾을 수 없습니다: " + strategyClass.getSimpleName())));
    }

    public ReportDataStrategy getStrategy(ReportType reportType) {
        ReportDataStrategy strategy = strategies.get(reportType);
        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 리포트 유형입니다: " + reportType);
        }
        return strategy;
    }
}
