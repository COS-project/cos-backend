package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalReader {

    private final LearningRepository learningRepository;

    public Goal read(TargetGoal targetGoal) {
        return learningRepository.getGoal(targetGoal);
    }

    public boolean exists(User user, Certificate certificate) {
        return learningRepository.existsGoal(user, certificate);
    }

    public Goal readRecentGoal(User user, Certificate certificate) {
        return learningRepository.getRecentGoal(user, certificate);
    }
}
