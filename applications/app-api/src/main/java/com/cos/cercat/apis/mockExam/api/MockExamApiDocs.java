package com.cos.cercat.apis.mockExam.api;

import com.cos.cercat.apis.mockExam.request.CreateMockExamRequest;
import com.cos.cercat.apis.mockExam.request.QuestionImageRequest;
import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "모의고사 API")
public interface MockExamApiDocs {

    @Operation(summary = "년도별 모의고사 가져오기")
    Response<List<MockExamResponse>> findMockExams(Long certificateId,
            Integer examYear,
            User currentUser);

    @Operation(summary = "자격증별 시험년도 조회")
    Response<List<Integer>> findExamYears(Long certificateId);

    @Operation(summary = "모의고사 문제들 가져오기")
    Response<List<QuestionResponse>> findQuestions(Long mockExamId);

    @Operation(summary = "모의고사 만들기")
    Response<Void> createMockExam(Long certificateId,
            CreateMockExamRequest request);

    @Operation(summary = "문제 이미지 업로드")
    Response<Void> insertQuestionImage(Long questionId, QuestionImageRequest request);


}
