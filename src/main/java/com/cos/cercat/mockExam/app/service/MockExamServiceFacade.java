package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.Member.domain.entity.User;
import com.cos.cercat.mockExam.api.request.MockExamResultRequest;
import com.cos.cercat.mockExam.api.request.SubjectResultRequest;
import com.cos.cercat.mockExam.api.request.UserAnswerRequest;
import com.cos.cercat.mockExam.api.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.api.response.QuestionResponse;
import com.cos.cercat.mockExam.domain.entity.*;
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

    @Transactional(readOnly = true)
    public List<MockExamWithResultResponse> getMockExamList(Long certificateId, Integer examYear, User user) {
        List<MockExamWithResultResponse> resultResponses = new ArrayList<>();
        List<MockExam> mockExamList = mockExamService.getMockExamByCertificateIdAndYear(certificateId, examYear);

        for (MockExam mockExam : mockExamList) {
            List<MockExamResult> userMockExamResults = mockExamResultService.getUserMockExamResults(mockExam, user);
            resultResponses.add(MockExamWithResultResponse.from(mockExam, userMockExamResults));
        }
        resultResponses.sort(Comparator.comparing(MockExamWithResultResponse::round));

        return resultResponses;
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionList(Long mockExamId) {

        MockExam mockExam = mockExamService.getMockExamById(mockExamId);

        return questionService.getQuestionListByMockExam(mockExam).stream()
                .map(QuestionResponse::from)
                .toList();
    }

    @Transactional
    public void createMockExamResult(Long mockExamId, MockExamResultRequest request, User user) {
        MockExam mockExam = mockExamService.getMockExamById(mockExamId);
        int beforeRound = mockExamResultService.getMockExamResultsCount(mockExam, user);

        MockExamResult mockExamResult = MockExamResult.of(mockExam, user, beforeRound + 1);

        mockExamResult.addAllSubjectResults(toSubjectResultEntities(request, user));

        mockExamResultService.save(mockExamResult);
    }

    private List<SubjectResult> toSubjectResultEntities(MockExamResultRequest request, User user) {
        List<SubjectResult> subjectResultList = new ArrayList<>();

        for (SubjectResultRequest subjectResultRequest : request.subjectResultRequests()) {
            Subject subject = subjectService.getSubjectById(subjectResultRequest.subjectId());

            SubjectResult subjectResult = subjectResultRequest.toEntity(subject);
            subjectResult.addAllUserAnswers(toUserAnswerEntities(user, subjectResultRequest.userAnswerRequests()));

            subjectResultList.add(subjectResult);
        }
        return subjectResultList;
    }

    private List<UserAnswer> toUserAnswerEntities(User user, List<UserAnswerRequest> requests) {

        List<UserAnswer> userAnswers = new ArrayList<>();

        for (UserAnswerRequest userAnswerRequest : requests) {
            Question question = questionService.getQuestionById(userAnswerRequest.questionId());
            userAnswers.add(userAnswerRequest.toEntity(question, user));
        }
        return userAnswers;
    }
}
