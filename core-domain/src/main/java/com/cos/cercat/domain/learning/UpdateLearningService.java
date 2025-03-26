package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateLearningService {

    private final UserReader userReader;
    private final GoalReader goalReader;
    private final GoalUpdater goalUpdater;
    private final PermissionValidator permissionValidator;

    public void updateGoal(UserId userId,
                           GoalId goalId,
                           NewGoal newGoal) {
        User user = userReader.read(userId);
        Goal goal = goalReader.read(goalId);
        permissionValidator.validate(goal, user);
        goal.updateGoal(newGoal);
        goalUpdater.update(goal);
    }
}
