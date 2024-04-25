package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectResultReader {

    private final MockExamResultRepository mockExamResultRepository;

    public List<SubjectResultStatistics> readStatistics(TargetUser targetUser,
                                                        TargetCertificate targetCertificate,
                                                        GoalPeriod goalDate) {
        return mockExamResultRepository.getSubjectResultStatistics(targetUser, targetCertificate, goalDate);
    }

}
