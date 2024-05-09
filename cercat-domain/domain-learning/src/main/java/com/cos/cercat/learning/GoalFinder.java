package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalFinder {

    private final LearningRepository learningRepository;

    public List<Goal> findAll(User user, Certificate certificate) {
        return learningRepository.findAllGoals(user, certificate);
    }

}
