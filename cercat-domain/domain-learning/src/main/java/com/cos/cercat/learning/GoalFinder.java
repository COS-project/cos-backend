package com.cos.cercat.learning;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
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
