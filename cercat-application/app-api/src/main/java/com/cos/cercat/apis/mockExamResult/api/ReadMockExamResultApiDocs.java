package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.dto.response.*;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.mockexamresult.DateCond;
import com.cos.cercat.domain.mockexamresult.DateType;
import com.cos.cercat.domain.mockexamresult.ReportType;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "성적 리포트 조회 API")
public interface ReadMockExamResultApiDocs {

    @Operation(summary = "성적 리포트 리스트 조회")
    Response<List<MockExamResultWithSubjectsResponse>> mockExamResultDetails(Long mockExamId,
                                                                             UserDTO user);

    @Operation(summary = "전체 틀린문제 조회")
    Response<SliceResult<UserAnswerResponse>> allWrongUserAnswers(@PageableDefault Pageable pageable,
                                                                  @PathVariable Long certificateId,
                                                                  @AuthenticationPrincipal UserDTO currentUser);

    @Operation(summary = "최근 모의고사 결과 조회")
    Response<MockExamResultResponse> recentMockExamResult(Long mockExamId,
                                                          UserDTO currentUser);

    @Operation(summary = "특정 모의고사 틀린문제 조회")
    Response<SliceResult<UserAnswerResponse>> wrongUserAnswers(Pageable pageable,
                                                               Long mockExamResultId,
                                                               UserDTO currentUser);

    @Operation(summary = "과목별 정답률 평균 및 머문 시간 평균 조회")
    Response<List<SubjectResultsAVGResponse>> subjectResultsAVG(Long certificateId,
                                                                UserDTO currentUser);

    @Operation(summary = "주간/월간/년간 성적 리포트 통계 조회")
    Response<ReportResponse> scoreReport(Long certificateId,
                                         ReportType reportType,
                                         DateCond dateCond,
                                         UserDTO currentUser);

    @Operation(summary = "날짜/주차/월 로 성적 리포트 조회")
    Response<PageResult<MockExamResultResponse>> mockExamResults(Long certificateId,
                                                                 DateType dateType,
                                                                 DateCond dateCond,
                                                                 UserDTO currentUser,
                                                                 Pageable pageable);
}
