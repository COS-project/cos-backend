package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.request.GoalRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "학습 수정 API")
public interface UpdateLearningApiDocs {

    @Operation(summary = "목표 수정")
    Response<Void> updateGoal(Long goalId,
                              GoalRequest request,
                              User currentUser);

}
