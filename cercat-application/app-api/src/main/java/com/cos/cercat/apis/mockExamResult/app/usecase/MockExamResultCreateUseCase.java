package com.cos.cercat.apis.mockExamResult.app.usecase;

import com.cos.cercat.apis.mockExamResult.dto.request.MockExamResultRequest;
import com.cos.cercat.apis.mockExamResult.dto.request.SubjectResultRequest;
import com.cos.cercat.apis.mockExamResult.dto.request.UserAnswerRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.*;
import com.cos.cercat.service.SubjectService;
import com.cos.cercat.service.MockExamService;
import com.cos.cercat.service.QuestionService;
import com.cos.cercat.service.MockExamResultService;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class MockExamResultCreateUseCase {

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
        UserEntity userEntity = userService.getUser(userId);
        MockExam mockExam = mockExamService.getMockExam(mockExamId);

        questionService.getQuestionListByMockExam(mockExam);// 문제정보 JPA 1차 캐싱
        int beforeRound = mockExamResultService.getMockExamResultsCount(mockExam, userEntity);
        SubjectResults subjectResults = toSubjectResults(request, userEntity);
        int totalScore = subjectResults.getSubjectResults().stream().mapToInt(SubjectResult::getScore).sum();


        MockExamResult mockExamResult = MockExamResult.of(mockExam, userEntity, beforeRound + 1, subjectResults, totalScore);
        log.info("userEntity - {}, mockExamId - {}, round - {}번째 모의고사 성적리포트 생성", userEntity.getEmail(), mockExamId, beforeRound + 1);
        return mockExamResultService.batchInsert(mockExamResult);
    }

    private SubjectResults toSubjectResults(MockExamResultRequest request, UserEntity userEntity) {

        return SubjectResults.from(request.subjectResultRequests().stream()
                .map(subjectResultRequest -> toSubjectResult(userEntity, subjectResultRequest))
                .toList());
    }

    private SubjectResult toSubjectResult(UserEntity userEntity, SubjectResultRequest subjectResultRequest) {
        SubjectEntity subjectEntity = subjectService.getSubject(subjectResultRequest.subjectId());
        UserAnswers userAnswers = toUserAnswers(userEntity, subjectResultRequest.userAnswerRequests());

        return subjectResultRequest.toEntity(subjectEntity, userAnswers);
    }

    private UserAnswers toUserAnswers(UserEntity userEntity, List<UserAnswerRequest> requests) {

        return UserAnswers.from(requests.stream()
                .map(userAnswerRequest -> toUserAnswer(userEntity, userAnswerRequest))
                .toList());
    }

    private UserAnswer toUserAnswer(UserEntity userEntity, UserAnswerRequest userAnswerRequest) {
        Question question = questionService.getQuestion(userAnswerRequest.questionId());
        return userAnswerRequest.toEntity(question, userEntity);
    }


}
