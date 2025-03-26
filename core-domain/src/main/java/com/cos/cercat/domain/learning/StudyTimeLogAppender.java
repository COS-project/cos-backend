package com.cos.cercat.domain.learning;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyTimeLogAppender {

    private final LearningRepository learningRepository;

    public void append(GoalId goalId,
                       Long studyTime) {
        learningRepository.saveStudyTimeLog(goalId, studyTime);
    }

}
