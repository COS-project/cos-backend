package com.cos.cercat.batch.alarm.noop;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamCacheRepository;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOPMockExamCacheRepository implements MockExamCacheRepository {

    @Override
    public void setQuestions(List<Question> questions) {

    }

    @Override
    public Optional<List<Question>> getQuestions(MockExam mockExam) {
        return Optional.empty();
    }

    @Override
    public void deleteQuestions(TargetMockExam targetMockExam) {

    }
}
