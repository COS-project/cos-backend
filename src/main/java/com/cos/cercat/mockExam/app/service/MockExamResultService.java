package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.Member.domain.entity.Member;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.api.request.MockExamResultRequest;
import com.cos.cercat.mockExam.api.request.SubjectResultRequest;
import com.cos.cercat.mockExam.api.request.UserAnswerRequest;
import com.cos.cercat.mockExam.app.dto.MockExamResultDTO;
import com.cos.cercat.mockExam.domain.entity.*;
import com.cos.cercat.mockExam.domain.repository.MockExamRepository;
import com.cos.cercat.mockExam.domain.repository.MockExamResultRepository;
import com.cos.cercat.mockExam.domain.repository.QuestionRepository;
import com.cos.cercat.mockExam.domain.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamResultService {

    private final MockExamResultRepository mockExamResultRepository;
    private final SubjectRepository subjectRepository;
    private final MockExamRepository mockExamRepository;
    private final QuestionRepository questionRepository;

    public void createMockExamResult(Long mockExamId, MockExamResultRequest request, Member member) {
        MockExam mockExam = getMockExam(mockExamId);

        MockExamResult mockExamResult = MockExamResult.of(mockExam, member, 1);

        mockExamResult.addAllSubjectResults(toSubjectResultEntities(request, member));

        mockExamResultRepository.save(mockExamResult);
    }

    public List<MockExamResultDTO> getMockExamResults(MockExam mockExam, Member member) {

        return mockExamResultRepository.findMockExamResultByMockExamAndMember(mockExam, member).stream()
                .map(MockExamResultDTO::from)
                .sorted(Comparator.comparing(MockExamResultDTO::examDate).reversed())
                .toList();
    }

    private MockExam getMockExam(Long mockExamId) {
        return mockExamRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    private Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() ->
                new CustomException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    private Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

    private List<SubjectResult> toSubjectResultEntities(MockExamResultRequest request, Member member) {
        List<SubjectResult> subjectResultList = new ArrayList<>();

        for (SubjectResultRequest subjectResultRequest : request.subjectResultRequests()) {
            Subject subject = getSubjectById(subjectResultRequest.subjectId());

            SubjectResult subjectResult = SubjectResult.of(subject, subjectResultRequest.score());
            subjectResult.addAllUserAnswers(toUserAnswerEntities(member, subjectResultRequest.userAnswerRequests()));

            subjectResultList.add(subjectResult);
        }
        return subjectResultList;
    }

    private List<UserAnswer> toUserAnswerEntities(Member member, List<UserAnswerRequest> requests) {

        List<UserAnswer> userAnswers = new ArrayList<>();

        for (UserAnswerRequest userAnswerRequest : requests) {
            Question question = getQuestionById(userAnswerRequest.questionId());

            UserAnswer userAnswer = UserAnswer.of(
                    member,
                    question,
                    userAnswerRequest.selectOption(),
                    userAnswerRequest.takenTime(),
                    userAnswerRequest.is_correct()
            );
            userAnswers.add(userAnswer);
        }
        return userAnswers;
    }
}
