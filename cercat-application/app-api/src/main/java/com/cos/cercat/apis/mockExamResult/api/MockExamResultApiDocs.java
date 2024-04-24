package com.cos.cercat.apis.mockExamResult.api;


import com.cos.cercat.apis.mockExamResult.dto.request.CreateMockExamResultRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.mockexamresult.TargetMockExamResult;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "성적 리포트 생성 API")
public interface MockExamResultApiDocs {

    @Operation(summary = "성적 리포트 생성 및 반환")
    Response<TargetMockExamResult> createMockExamResult(Long mockExamId,
                                                        CreateMockExamResultRequest request,
                                                        UserDTO currentUser);

}

