package com.cos.cercat.goal.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.goal.app.GoalCreateService;
import com.cos.cercat.goal.dto.request.GoalCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "목표 생성 API")
public class GoalCreateApi {

    private final GoalCreateService goalCreateService;

    @PostMapping("/{certificateId}/goals")
    @Operation(summary = "목표 생성")
    public Response<Void> createGoal(@PathVariable Long certificateId,
                                     @RequestBody GoalCreateRequest request) {
        goalCreateService.createGoal(request, certificateId, 1L);

        return Response.success("목표 생성 성공");
    }
}
