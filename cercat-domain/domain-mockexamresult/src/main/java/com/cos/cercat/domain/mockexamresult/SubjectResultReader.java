package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectResultReader {

    private final MockExamResultRepository mockExamResultRepository;

    public List<SubjectResultStatistics> readStatistics(User user,
                                                        Certificate certificate,
                                                        GoalPeriod goalDate) {
        return mockExamResultRepository.getSubjectResultStatistics(user, certificate, goalDate);
    }

}
