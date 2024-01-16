package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.app.SubjectService;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.app.QuestionService;
import com.cos.cercat.mockExam.domain.*;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.domain.SubjectResult;
import com.cos.cercat.mockExamResult.domain.UserAnswer;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExamResult.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExamResult.dto.request.SubjectResultRequest;
import com.cos.cercat.mockExamResult.dto.request.UserAnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamResultCreateService {

    private final MockExamResultService mockExamResultService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final SubjectService subjectService;
    private final UserService userService;

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

        mockExamResult.addAllSubjectResults(toSubjectResults(request, user));

        mockExamResultService.save(mockExamResult);
    }

    private List<SubjectResult> toSubjectResults(MockExamResultRequest request, User user) {

        return request.subjectResultRequests().stream()
                .map(subjectResultRequest -> toSubjectResult(user, subjectResultRequest))
                .toList();
    }

    private SubjectResult toSubjectResult(User user, SubjectResultRequest subjectResultRequest) {
        Subject subject = subjectService.getSubject(subjectResultRequest.subjectId());
        SubjectResult subjectResult = subjectResultRequest.toEntity(subject);
        return subjectResult.addAllUserAnswers(toUserAnswers(user, subjectResultRequest.userAnswerRequests()));
    }

    private List<UserAnswer> toUserAnswers(User user, List<UserAnswerRequest> requests) {

        return requests.stream()
                .map(userAnswerRequest -> toUserAnswer(user, userAnswerRequest))
                .toList();
    }

    private UserAnswer toUserAnswer(User user, UserAnswerRequest userAnswerRequest) {
        Question question = questionService.getQuestion(userAnswerRequest.questionId());
        return userAnswerRequest.toEntity(question, user);
    }


}
