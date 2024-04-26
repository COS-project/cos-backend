package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
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
