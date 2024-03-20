package com.cos.cercat.learning.app.facade;

import com.cos.cercat.learning.app.GoalService;
import com.cos.cercat.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LearningUpdateService {

    private final GoalService goalService;
    private final UserService userService;

    /***
     * 유저의 목표를 수정합니다.
     * @param goalId 목표 ID
     * @param request 목표 수정 정보
     * @param userId 유저 ID
     */
    @Transactional
    public void updateGoal(Long goalId, GoalUpdateRequest request, Long userId) {
        User user = userService.getUser(userId);
        log.info("user - {}, goalId - {} 목표 수정", user.getEmail(), goalId);
        goalService.updateGoal(request, goalId, user);
    }

}
