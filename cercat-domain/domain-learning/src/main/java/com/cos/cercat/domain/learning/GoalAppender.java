package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GoalAppender {

    private final LearningRepository learningRepository;

    public void append(User user,
                       Certificate certificate,
                       NewGoal newGoal) {
        learningRepository.save(user, certificate, newGoal);

    }
}
