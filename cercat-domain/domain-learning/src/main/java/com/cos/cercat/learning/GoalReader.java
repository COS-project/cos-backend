package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
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

    public boolean exists(TargetCertificate targetCertificate, TargetUser targetUser) {
        return learningRepository.existsGoal(targetCertificate, targetUser);
    }

    public Goal readRecentGoal(User user, Certificate certificate) {
        return learningRepository.getRecentGoal(user, certificate);
    }
}
