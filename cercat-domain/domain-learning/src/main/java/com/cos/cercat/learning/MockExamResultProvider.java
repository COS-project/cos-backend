package com.cos.cercat.learning;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;

public interface MockExamResultProvider {
    int readCurrentMaxScore(TargetCertificate targetCertificate, TargetUser targetUser, GoalPeriod goalPeriod);
    int countTodayMockExamResults(TargetCertificate targetCertificate, TargetUser targetUser);
    int countTotalMockExamResults(TargetCertificate targetCertificate, TargetUser targetUser, GoalPeriod goalPeriod);
}