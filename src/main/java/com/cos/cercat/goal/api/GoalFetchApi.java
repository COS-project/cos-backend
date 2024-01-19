package com.cos.cercat.goal.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.goal.app.GoalFetchService;
import com.cos.cercat.goal.dto.response.GoalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "목표 조회 API")
public class GoalFetchApi {

    private final GoalFetchService goalFetchService;

    @GetMapping("/{certificateId}/goals")
    @Operation(summary = "목표 상세 조회")
    public Response<GoalResponse> getGoalDetail(@PathVariable Long certificateId) {
        return Response.success(goalFetchService.getGoal(certificateId, 1L));
    }
}
