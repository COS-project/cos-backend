package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalReader {

    private final LearningRepository learningRepository;

    public Goal read(GoalId goalId) {
        return learningRepository.getGoal(goalId);
    }

    public boolean exists(User user, Certificate certificate) {
        return learningRepository.existsGoal(user, certificate);
    }

    public Goal readRecentGoal(User user, Certificate certificate) {
        return learningRepository.getRecentGoal(user, certificate);
    }
}
