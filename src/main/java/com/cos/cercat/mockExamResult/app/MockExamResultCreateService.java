package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.app.SubjectService;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.app.QuestionService;
import com.cos.cercat.mockExam.domain.*;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.mockExamResult.domain.*;
import com.cos.cercat.mockExamResult.dto.response.MockExamResultWithSubjectsResponse;
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
     * @return 모의고사 결과 정보를 반환합니다.
     * */
    @Transactional
    public long createMockExamResult(Long mockExamId, MockExamResultRequest request, Long userId) {
        User user = userService.getUser(userId);
        MockExam mockExam = mockExamService.getMockExam(mockExamId);

        questionService.getQuestionListByMockExam(mockExam);// 문제정보 JPA 1차 캐싱
        int beforeRound = mockExamResultService.getMockExamResultsCount(mockExam, user);
        SubjectResults subjectResults = toSubjectResults(request, user);
        int totalScore = subjectResults.getSubjectResults().stream().mapToInt(SubjectResult::getScore).sum();


        MockExamResult mockExamResult = MockExamResult.of(mockExam, user, beforeRound + 1, subjectResults, totalScore);
        log.info("user - {}, mockExamId - {}, round - {}번째 모의고사 성적리포트 생성", user.getEmail(), mockExamId, beforeRound + 1);
        return mockExamResultService.batchInsert(mockExamResult);
    }

    private SubjectResults toSubjectResults(MockExamResultRequest request, User user) {

        return SubjectResults.from(request.subjectResultRequests().stream()
                .map(subjectResultRequest -> toSubjectResult(user, subjectResultRequest))
                .toList());
    }

    private SubjectResult toSubjectResult(User user, SubjectResultRequest subjectResultRequest) {
        Subject subject = subjectService.getSubject(subjectResultRequest.subjectId());
        UserAnswers userAnswers = toUserAnswers(user, subjectResultRequest.userAnswerRequests());

        return subjectResultRequest.toEntity(subject, userAnswers);
    }

    private UserAnswers toUserAnswers(User user, List<UserAnswerRequest> requests) {

        return UserAnswers.from(requests.stream()
                .map(userAnswerRequest -> toUserAnswer(user, userAnswerRequest))
                .toList());
    }

    private UserAnswer toUserAnswer(User user, UserAnswerRequest userAnswerRequest) {
        Question question = questionService.getQuestion(userAnswerRequest.questionId());
        return userAnswerRequest.toEntity(question, user);
    }


}
