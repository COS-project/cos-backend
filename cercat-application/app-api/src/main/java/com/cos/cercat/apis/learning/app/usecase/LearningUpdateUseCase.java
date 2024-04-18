package com.cos.cercat.apis.learning.app.usecase;

import com.cos.cercat.apis.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.service.GoalService;
import com.cos.cercat.service.UserService;
import com.cos.cercat.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        UserEntity userEntity = userService.getUser(userId);
        log.info("userEntity - {}, goalId - {} 목표 수정", userEntity.getEmail(), goalId);
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
