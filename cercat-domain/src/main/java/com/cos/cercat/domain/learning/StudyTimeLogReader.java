package com.cos.cercat.domain.learning;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyTimeLogReader {

    private final LearningRepository learningRepository;


    public long readTodayStudyTime(Goal goal) {

        return learningRepository.getTodayStudyTime(goal.getId());
    }

    public long readTotalStudyTime(Goal goal) {
        return learningRepository.getTotalStudyTime(goal.getId());
    }
}
