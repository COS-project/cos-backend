package com.cos.cercat.apis.examReview.api;


import com.cos.cercat.apis.examReview.request.ExamReviewCreateRequest;
import com.cos.cercat.apis.examReview.response.ExamReviewResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "따끈 후기 API")
public interface ExamReviewApiDocs {

    @Operation(summary = "따끈후기 작성")
    Response<Void> createExamReview(Long certificateId,
                                    ExamReviewCreateRequest request,
                                    UserDTO currentUser);

    @Operation(summary = "최근 시험의 따끈 후기 조회")
    Response<SliceResult<ExamReviewResponse>> getExamReviews(Long certificateId,
                                                            ExamReviewSearchCond cond,
                                                            Pageable pageable);

    @Operation(summary = "따끈후기 작성 대상자인지 조회")
    Response<Boolean> checkReviewDateAfterExamDate(Long certificateId, UserDTO currentUser);

}
