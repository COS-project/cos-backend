package com.cos.cercat.learning.app;

import com.cos.cercat.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LearningUpdateService {

    private final GoalService goalService;
    private final UserService userService;

    @Transactional
    public void updateGoal(Long goalId, GoalUpdateRequest request, Long userId) {

        User user = userService.getUser(userId);
        goalService.updateGoal(request, goalId, user);
    }

}
