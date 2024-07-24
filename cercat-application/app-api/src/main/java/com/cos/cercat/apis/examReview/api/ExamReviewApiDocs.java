package com.cos.cercat.apis.examReview.api;


import com.cos.cercat.apis.examReview.request.ExamReviewCreateRequest;
import com.cos.cercat.apis.examReview.response.ExamReviewResponse;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "따끈 후기 API")
public interface ExamReviewApiDocs {

    @Operation(summary = "따끈후기 작성")
    Response<Void> createExamReview(Long certificateId,
                                    ExamReviewCreateRequest request,
                                    User currentUser);

    @Operation(summary = "최근 시험의 따끈 후기 조회")
    Response<SliceResult<ExamReviewResponse>> getExamReviews(Long certificateId,
                                                            ExamReviewSearchCond cond,
                                                             @Parameter(examples = {
                                                                     @ExampleObject(name = "cursor", value = """ 
                                                                    {
                                                                        "page" : 0,
                                                                        "size" : 10,
                                                                        "sortFields" : "createdAt, id",
                                                                        "sortDirections" : "DESC, ASC"
                                                                    }
                                                                """)
                                                             })
                                                            Cursor cursor);

    @Operation(summary = "따끈후기 작성 대상자인지 조회")
    Response<Boolean> checkReviewDateAfterExamDate(Long certificateId, User currentUser);

}
