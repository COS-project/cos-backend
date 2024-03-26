package com.cos.cercat.apis.mockExamResult.app.usecase;

import com.cos.cercat.apis.mockExamResult.dto.request.DateType;
import com.cos.cercat.apis.mockExamResult.dto.request.ReportType;
import com.cos.cercat.apis.mockExamResult.dto.response.*;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.certificate.service.CertificateService;
import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.learning.service.GoalService;
import com.cos.cercat.domain.learning.domain.Goal;
import com.cos.cercat.domain.mockExam.service.MockExamService;
import com.cos.cercat.domain.mockExam.domain.MockExam;
import com.cos.cercat.domain.mockExamResult.service.MockExamResultService;
import com.cos.cercat.domain.mockExamResult.service.SubjectResultService;
import com.cos.cercat.domain.mockExamResult.service.UserAnswerService;
import com.cos.cercat.domain.mockExamResult.domain.MockExamResult;
import com.cos.cercat.domain.mockExamResult.dto.DateCond;
import com.cos.cercat.domain.user.service.UserService;
import com.cos.cercat.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MockExamResultFetchUseCase {

    private final MockExamResultService mockExamResultService;
    private final MockExamService mockExamService;
    private final UserService userService;
    private final CertificateService certificateService;
    private final UserAnswerService userAnswerService;
    private final SubjectResultService subjectResultService;
    private final GoalService goalService;

    /***
     * 유저의 해당 모의고사의 성적리포트를 모두 조회
     * @param mockExamId 모의고사 ID
     * @param userId 유저 ID
     * @return 리스트 형태의 성적리포트 Reponse DTO를 반환합니다.
     */
    public List<MockExamResultWithSubjectsResponse> getMockExamResults(Long mockExamId, Long userId) {
        MockExam mockExam = mockExamService.getMockExam(mockExamId);
        User user = userService.getUser(userId);

        List<MockExamResult> mockExamResults = mockExamResultService.getMockExamResults(mockExam, user);
        log.info("user - {}, mockExamId - {} 성적리포트 리스트 조회", user.getEmail(), mockExamId);
        return mockExamResults.stream()
                .map(MockExamResultWithSubjectsResponse::from)
                .toList();
    }

    /***
     * 유저의 해당 자격증시험의 모든 틀린 문제를 조회한다.
     * @param pageable 페이징 정보
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 슬라이스 형태로 문제 정보를 포함하여 유저가 제출한 답을 반환한다.
     */
    public Slice<UserAnswerResponse> getAllWrongUserAnswers(Pageable pageable, Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        log.info("user - {}, certificate - {} 모든 오답 조회", user.getEmail(), certificate.getCertificateName());
        return userAnswerService.getAllWrongUserAnswers(pageable, user, certificate).map(UserAnswerResponse::from);
    }

    /***
     * 유저의 해당 모의고사 시험의 틀린문제를 모두 조회한다.
     * @param pageable 페이징 정보
     * @param mockExamResultId 모의고사 결과 ID
     * @return 슬라이스 형태로 문제 정보를 포함하여 유저가 제출한 답을 반환한다.
     */
    public Slice<UserAnswerResponse> getWrongUserAnswers(Pageable pageable, Long mockExamResultId, Long userId) {
        User user = userService.getUser(userId);
        MockExamResult mockExamResult = mockExamResultService.getMockExamResult(mockExamResultId);

        if (!mockExamResult.isAuthorized(user)) {
            throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
        }

        log.info("mockExamResultId - {} 모든 오답 조회", mockExamResultId);
        return userAnswerService.getWrongUserAnswers(pageable, mockExamResult).map(UserAnswerResponse::from);
    }

    /***
     * 유저의 목표를 설정한 이후의 해당 자격증 시험의 과목별 정답률 평균 및 머문 시간 평균 조회한다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 과목 정답률 평균 및 머문시간 평균을 리스트 형태로 반환한다.
     */
    public List<SubjectResultsAVGResponse> getSubjectResultsAVG(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal recentGoal = goalService.getRecentGoal(user, certificate);

        log.info("user - {}, certificate - {} 과목별 정답률 및 머문시간 평균 조회", user.getEmail(), certificate.getCertificateName());
        return subjectResultService.getSubjectResultsAVG(certificate, user, recentGoal.getPrepareStartDateTime()).stream()
                .map(SubjectResultsAVGResponse::from)
                .toList();
    }

    public Report getReport(Long certificateId, ReportType reportType, DateCond dateCond, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        return switch (reportType) {
            case WEEK -> Report.from(mockExamResultService.getWeeklyReport(certificate, user, dateCond));
            case MONTH -> Report.from(mockExamResultService.getMonthlyReport(certificate, user, dateCond));
            case YEAR -> Report.from(mockExamResultService.getYearlyReport(certificate, user, dateCond));
        };
    }

    public Slice<MockExamResultResponse> getMockExamResultsByDate(Long certificateId,
                                                                  Long userId,
                                                                  DateType dateType,
                                                                  DateCond dateCond,
                                                                  Pageable pageable) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        return switch (dateType) {
            case DATE -> mockExamResultService.getMockExamResultsByDate(certificate, user, dateCond.date(), pageable)
                    .map(MockExamResultResponse::from);
            case WEEK_OF_MONTH -> mockExamResultService.getMockExamResultsByWeekOfMonth(certificate, user, dateCond.weekOfMonth(), pageable)
                    .map(MockExamResultResponse::from);
            case MONTH -> mockExamResultService.getMockExamResultsByMonth(certificate, user, dateCond.month(), pageable)
                    .map(MockExamResultResponse::from);
        };
    }

}
