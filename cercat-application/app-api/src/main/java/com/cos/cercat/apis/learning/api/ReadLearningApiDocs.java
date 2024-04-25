package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.dto.response.GoalDetailResponse;
import com.cos.cercat.apis.learning.dto.response.GoalResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.learning.GoalAchievement;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "학습 조회 API")
public interface ReadLearningApiDocs {

    @Operation(summary = "유저의 현재까지의 모든 목표 조회")
    Response<List<GoalResponse>> getAllGoal(Long certificateId, UserDTO currentUser);

    @Operation(summary = "목표 상세 조회")
    Response<GoalDetailResponse> getGoalDetail(Long goalId);

    @Operation(summary = "목표 달성도 조회")
    Response<GoalAchievement> getGoalAchievement(Long certificateId, UserDTO currentUser);

    @Operation(summary = "유저 목표 설정 여부 조회")
    Response<Boolean> existsGoal(Long certificateId, UserDTO currentUser);



}

