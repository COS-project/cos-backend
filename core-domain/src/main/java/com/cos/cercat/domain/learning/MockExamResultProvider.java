package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

public interface MockExamResultProvider {
    int readCurrentMaxScore(User user,
                            Certificate certificate,
                            GoalPeriod goalPeriod);

    int countTodayMockExamResults(User user,
                                  Certificate certificate);

    int countTotalMockExamResults(User user,
                                  Certificate certificate,
                                  GoalPeriod goalPeriod);
}