package com.cos.cercat.apis.mockExamResult.api;


import com.cos.cercat.apis.mockExamResult.request.CreateMockExamResultRequest;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.mockexamresult.MockExamResultId;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "성적 리포트 생성 API")
public interface MockExamResultApiDocs {

    @Operation(summary = "성적 리포트 생성 및 반환")
    Response<MockExamResultId> createMockExamResult(Long mockExamId,
                                                        CreateMockExamResultRequest request,
                                                        User currentUser);

}

