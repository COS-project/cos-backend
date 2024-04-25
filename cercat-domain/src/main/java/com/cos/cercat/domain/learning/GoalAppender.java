package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GoalAppender {

    private final LearningRepository learningRepository;

    public void append(TargetUser targetUser,
                       TargetCertificate targetCertificate,
                       NewGoal newGoal) {
        learningRepository.save(targetUser, targetCertificate, newGoal);

    }
}
