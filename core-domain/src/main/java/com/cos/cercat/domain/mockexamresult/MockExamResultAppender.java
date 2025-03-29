package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamResultAppender {

    private final MockExamResultRepository mockExamResultRepository;
    private final MockExamResultReader mockExamResultReader;
    private final ScoreDataCache scoreDataCache;

    public MockExamResultId append(
            User user,
            MockExam mockExam,
            List<NewSubjectResult> newSubjectResults
    ) {
        int round = mockExamResultReader.count(user, mockExam) + 1;
        scoreDataCache.delete(user, mockExam.certificate());
        return mockExamResultRepository.save(
                user,
                mockExam,
                NewMockExamResult.of(round, newSubjectResults)
        );
    }
}
