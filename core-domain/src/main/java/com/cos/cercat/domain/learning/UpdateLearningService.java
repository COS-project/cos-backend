package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.TargetUser;
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

    public void updateGoal(TargetUser targetUser,
                           TargetGoal targetGoal,
                           NewGoal newGoal) {
        User user = userReader.read(targetUser);
        Goal goal = goalReader.read(targetGoal);
        permissionValidator.validate(goal, user);
        goal.updateGoal(newGoal);
        goalUpdater.update(goal);
    }
}
