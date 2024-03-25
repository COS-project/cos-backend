package com.cos.cercat.learning.app.usecase;

import com.cos.cercat.annotation.UseCase;
import com.cos.cercat.learning.service.GoalService;
import com.cos.cercat.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.user.service.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class LearningUpdateUseCase {

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
        goalService.updateGoalBuilder()
                .goalId(goalId)
                .goalScore(request.goalScore())
                .goalPrepareDays(request.goalPrepareDays())
                .goalStudyTime(request.goalStudyTime())
                .goalMockExams(request.goalMockExams())
                .mockExamsPerDay(request.mockExamsPerDay())
                .prepareFinishDateTime(request.prepareFinishDateTime())
                .prepareStartDateTime(request.prepareStartDateTime())
                .studyRepeatDays(request.studyRepeatDays())
                .studyTimePerDay(request.studyTimePerDay())
                .mockExamRepeatDays(request.mockExamRepeatDays())
                .build();
    }

}
