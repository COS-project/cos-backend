package com.cos.cercat.goal.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.goal.domain.Goal;
import com.cos.cercat.goal.repository.GoalRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    public void createGoal(Goal goal) {
        goalRepository.save(goal);
    }

    public Goal getGoal(User user, Certificate certificate) {
        return goalRepository.findGoalByUserAndCertificate(user, certificate);
    }

}
