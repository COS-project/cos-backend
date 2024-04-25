package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalFinder {

    private final LearningRepository learningRepository;

    public List<Goal> findAll(TargetCertificate targetCertificate, TargetUser targetUser) {
        return learningRepository.findAllGoals(targetCertificate, targetUser);
    }

}
