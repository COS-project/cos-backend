package com.cos.cercat.learning.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.learning.app.LearningFetchService;
import com.cos.cercat.learning.dto.response.GoalAchievementResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "학습 조회 API")
public class LearningFetchApi {

    private final LearningFetchService learningFetchService;

    @GetMapping("/{certificateId}/goals")
    @Operation(summary = "목표 상세 조회")
    public Response<GoalResponse> getGoalDetail(@PathVariable Long certificateId,
                                                @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchService.getGoal(certificateId, user.getId()));
    }

    @GetMapping("/{certificateId}/goals/achievement")
    @Operation(summary = "목표 달성도 조회")
    public Response<GoalAchievementResponse> getGoalAchievement(@PathVariable Long certificateId,
                                                                @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchService.getGoalAchievement(certificateId, user.getId()));
    }

    @GetMapping("/{certificateId}/goal-status")
    @Operation(summary = "유저 목표 설정 여부 조회")
    public Response<Boolean> existsGoal(@PathVariable Long certificateId,
                                        @AuthenticationPrincipal UserDTO user) {
        return Response.success(learningFetchService.existsGoal(certificateId, user.getId()));
    }

}
