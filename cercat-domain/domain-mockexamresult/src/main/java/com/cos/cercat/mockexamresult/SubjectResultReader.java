package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.user.User;
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
