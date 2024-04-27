package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.learning.IMockExamResultReader;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamResultReader implements IMockExamResultReader {

    private final MockExamResultRepository mockExamResultRepository;

    public MockExamResult read(TargetMockExamResult targetMockExamResult) {
        return mockExamResultRepository.find(targetMockExamResult);
    }

    public int count(TargetUser targetUser, TargetMockExam targetMockExam) {
        return mockExamResultRepository.countMockExamResult(targetUser, targetMockExam);
    }

    public int readCurrentMaxScore(TargetCertificate targetCertificate,
                                   TargetUser targetUser,
                                   GoalPeriod goalPeriod) {
        return mockExamResultRepository.getCurrentMaxScore(targetCertificate, targetUser, goalPeriod);
    }

    public int countTodayMockExamResults(TargetCertificate targetCertificate,
                                          TargetUser targetUser) {
        return mockExamResultRepository.countTodayMockExamResults(targetCertificate, targetUser);
    }

    public int countTotalMockExamResults(TargetCertificate targetCertificate,
                                          TargetUser targetUser,
                                          GoalPeriod goalPeriod) {
        return mockExamResultRepository.countTotalMockExamResults(targetCertificate, targetUser, goalPeriod);
    }

    public MockExamResult readRecent(MockExam mockExam, User user) {
        return mockExamResultRepository.readRecent(mockExam, user);
    }
}
