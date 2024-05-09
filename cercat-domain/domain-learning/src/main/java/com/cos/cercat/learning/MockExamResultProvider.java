package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.User;

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