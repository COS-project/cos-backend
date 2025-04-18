package com.cos.cercat.domain.learning;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalUpdater {

    private final LearningRepository learningRepository;

    public void update(Goal goal) {
        learningRepository.save(goal);
    }
}
