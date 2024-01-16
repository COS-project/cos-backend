package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.app.SubjectService;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExam.domain.*;
import com.cos.cercat.question.app.QuestionService;
import com.cos.cercat.question.domain.Question;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExam.dto.request.SubjectResultRequest;
import com.cos.cercat.mockExam.dto.request.UserAnswerRequest;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.question.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamServiceFacade {

    private final MockExamResultService mockExamResultService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final CertificateService certificateService;

    /**
     * 자격증 ID와 시험년도, 유저정보를 통해 특정 자격증의 시험년도에 해당하는 모의고사들을 가져온다.
     * 유저가 모의고사를 푼 이력이있다면 모의고사 점수도 같이 반환한다.
     *
     * @param certificateId 자격증 고유 ID
     * @param examYear 시험년도
     * @param userId 로그인한 유저
     * */
    @Transactional(readOnly = true)
    public List<MockExamWithResultResponse> getMockExamList(Long certificateId, Integer examYear, Long userId) {
        List<MockExamWithResultResponse> resultResponses = new ArrayList<>();

        List<MockExam> mockExamList = mockExamService.getMockExamList(certificateService.getCertificate(certificateId), examYear);
        User user = userService.getUser(userId);

        for (MockExam mockExam : mockExamList) {
            List<MockExamResult> userMockExamResults = mockExamResultService.getUserMockExamResults(mockExam, user);
            resultResponses.add(MockExamWithResultResponse.from(mockExam, userMockExamResults));
        }
        resultResponses.sort(Comparator.comparing(MockExamWithResultResponse::round));

        return resultResponses;
    }


    /**
     * 모의고사 ID와 클라이언트에서 채점한 시험결과, 유저정보를 통해 시험 결과를 저장한다.
     *
     * @param mockExamId 모의고사 고유 ID
     * @param request 모의고사 채점 결과
     * @param userId 유저 정보
     * */
    @Transactional
    public void createMockExamResult(Long mockExamId, MockExamResultRequest request, Long userId) {
        User user = userService.getUser(userId);
        MockExam mockExam = mockExamService.getMockExamById(mockExamId);
        int beforeRound = mockExamResultService.getMockExamResultsCount(mockExam, user);

        MockExamResult mockExamResult = MockExamResult.of(mockExam, user, beforeRound + 1);

        mockExamResult.addAllSubjectResults(toSubjectResultEntities(request, user));

        mockExamResultService.save(mockExamResult); //
    }

    private List<SubjectResult> toSubjectResultEntities(MockExamResultRequest request, User user) {
        List<SubjectResult> subjectResultList = new ArrayList<>();

        for (SubjectResultRequest subjectResultRequest : request.subjectResultRequests()) {
            Subject subject = subjectService.getSubject(subjectResultRequest.subjectId());

            SubjectResult subjectResult = subjectResultRequest.toEntity(subject);
            subjectResult.addAllUserAnswers(toUserAnswerEntities(user, subjectResultRequest.userAnswerRequests()));

            subjectResultList.add(subjectResult);
        }
        return subjectResultList;
    }

    private List<UserAnswer> toUserAnswerEntities(User user, List<UserAnswerRequest> requests) {

        List<UserAnswer> userAnswers = new ArrayList<>();

        for (UserAnswerRequest userAnswerRequest : requests) {
            Question question = questionService.getQuestion(userAnswerRequest.questionId());
            userAnswers.add(userAnswerRequest.toEntity(question, user));
        }
        return userAnswers;
    }
}
