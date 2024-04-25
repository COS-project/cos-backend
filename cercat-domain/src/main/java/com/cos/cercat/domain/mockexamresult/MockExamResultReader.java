package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamResultReader {

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
}
