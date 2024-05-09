package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.learning.MockExamResultProvider;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamResultReader implements MockExamResultProvider {

    private final MockExamResultRepository mockExamResultRepository;

    public MockExamResult read(TargetMockExamResult targetMockExamResult) {
        return mockExamResultRepository.find(targetMockExamResult);
    }

    public int count(User user, MockExam mockExam) {
        return mockExamResultRepository.countMockExamResult(user, mockExam);
    }

    public int readCurrentMaxScore(User user,
                                   Certificate certificate,
                                   GoalPeriod goalPeriod) {
        return mockExamResultRepository.getCurrentMaxScore(user, certificate, goalPeriod);
    }

    public int countTodayMockExamResults(User user,
                                         Certificate certificate) {
        return mockExamResultRepository.countTodayMockExamResults(user, certificate);
    }

    public int countTotalMockExamResults(User user,
                                         Certificate certificate,
                                         GoalPeriod goalPeriod) {
        return mockExamResultRepository.countTotalMockExamResults(user, certificate, goalPeriod);
    }

    public MockExamResult readRecent(MockExam mockExam, User user) {
        return mockExamResultRepository.readRecent(mockExam, user);
    }
}
