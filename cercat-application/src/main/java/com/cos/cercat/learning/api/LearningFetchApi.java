package com.cos.cercat.learning.api;

import com.cos.cercat.dto.Response;
import com.cos.cercat.learning.app.usecase.LearningFetchUseCase;
import com.cos.cercat.learning.dto.response.GoalAchievementResponse;
import com.cos.cercat.learning.dto.response.GoalDetailResponse;
import com.cos.cercat.learning.dto.response.GoalResponse;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "학습 조회 API")
public class LearningFetchApi {

    private final LearningFetchUseCase learningFetchUseCase;

    @GetMapping("/certificates/{certificateId}/goals")
    @Operation(summary = "유저의 현재까지의 모든 목표 조회")
    public Response<List<GoalResponse>> getAllGoal(@PathVariable Long certificateId,
                                                   @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchUseCase.getAllGoals(certificateId, user.getId()));
    }

    @GetMapping("/goals/{goalId}")
    @Operation(summary = "목표 상세 조회")
    public Response<GoalDetailResponse> getGoalDetail(@PathVariable Long goalId,
                                                      @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchUseCase.getGoal(goalId, user));
    }
    @GetMapping("/certificates/{certificateId}/goals/achievement")
    @Operation(summary = "목표 달성도 조회")
    public Response<GoalAchievementResponse> getGoalAchievement(@PathVariable Long certificateId,
                                                                @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchUseCase.getGoalAchievement(certificateId, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/goal-status")
    @Operation(summary = "유저 목표 설정 여부 조회")
    public Response<Boolean> existsGoal(@PathVariable Long certificateId,
                                        @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchUseCase.existsGoal(certificateId, user.getId()));
    }

}
