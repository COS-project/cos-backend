package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "성적 리포트 수정 API")
public interface UpdateMockExamResultApiDocs {

    @Operation(summary = "틀린 문제 더이상 보지 않기")
    Response<Void> reviewUserAnswers(Long userAnswerId,
                                     User currentUser);

}
