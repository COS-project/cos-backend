//package com.cos.cercat.apis.examReview.api;
//
//import com.cos.cercat.apis.examReview.app.usecase.ExamReviewCreateUseCase;
//import com.cos.cercat.apis.examReview.dto.request.ExamReviewCreateRequest;
//import com.cos.cercat.common.domain.Response;
//import com.cos.cercat.dto.UserDTO;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1")
//@Tag(name = "따끈 후기 생성 API")
//public class ExamReviewCreateApi {
//
//    private final ExamReviewCreateUseCase examReviewCreateUseCase;
//
//    @PostMapping("/certificates/{certificateId}/exam-reviews")
//    @Operation(summary = "따끈후기 작성")
//    public Response<Void> createExamReview(@PathVariable Long certificateId,
//                                           @RequestBody ExamReviewCreateRequest request,
//                                           @AuthenticationPrincipal UserDTO currentUser) {
//        examReviewCreateUseCase.createExamReview(request, certificateId, currentUser.getId());
//        return Response.success("따끈 후기 생성 성공");
//    }
//}
