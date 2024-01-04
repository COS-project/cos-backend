package com.cos.cercat.mockExam.api;

import com.cos.cercat.common.Response;
import com.cos.cercat.mockExam.api.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.api.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.app.service.MockExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mock-exams")
public class MockExamController {

    private final MockExamService mockExamService;

    @GetMapping
    public Response<List<MockExamWithResultResponse>> getMockExamList(@RequestBody MockExamSearchRequest request) {
        List<MockExamWithResultResponse> responses = mockExamService.getMockExamList(request.certificateId(), request.examYear(), null);
        return Response.success(responses);
    }
}
