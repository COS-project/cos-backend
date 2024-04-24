package com.cos.cercat.repository;


import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.*;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.mockexamresult.*;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class MockExamResultRepositoryImpl implements MockExamResultRepository {

    private final MockExamResultJpaRepository mockExamResultJpaRepository;
    private final SubjectResultJpaRepository subjectResultJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final UserAnswerJpaRepository userAnswerJpaRepository;
    private final MockExamJpaRepository mockExamJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    @Transactional
    public TargetMockExamResult save(TargetUser targetUser, TargetMockExam targetMockExam, NewMockExamResult newMockExamResult) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        MockExamEntity mockExamEntity = mockExamJpaRepository.getReferenceById(targetMockExam.mockExamId());

        MockExamResultEntity mockExamResultEntity = MockExamResultEntity.builder()
                .mockExamEntity(mockExamEntity)
                .userEntity(userEntity)
                .totalScore(newMockExamResult.totalScore())
                .round(newMockExamResult.round())
                .build();

        MockExamResultEntity savedResult = mockExamResultJpaRepository.save(mockExamResultEntity);

        newMockExamResult.newSubjectResults().forEach(newSubjectResult -> {
            SubjectResultEntity subjectResultEntity = saveSubjectResult(newSubjectResult, savedResult);
            newSubjectResult.newUserAnswers().forEach(newUserAnswer -> saveUserAnswer(newUserAnswer, subjectResultEntity, userEntity));
        });

        return TargetMockExamResult.from(savedResult.getId());
    }

    @Override
    public int countMockExamResult(TargetUser targetUser, TargetMockExam targetMockExam) {
        return mockExamResultJpaRepository.countMockExamResults(targetUser.userId(), targetMockExam.mockExamId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserAnswer find(TargetUserAnswer targetUserAnswer) {
        UserAnswerEntity userAnswerEntity = userAnswerJpaRepository.findById(targetUserAnswer.userAnswerId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
        return userAnswerEntity.toDomain();
    }

    @Override
    public void updateUserAnswer(UserAnswer userAnswer) {
        SubjectResultEntity subjectResultEntity = subjectResultJpaRepository.getReferenceById(userAnswer.getSubjectResultId());
        userAnswerJpaRepository.save(UserAnswerEntity.from(userAnswer, subjectResultEntity));
    }

    private SubjectResultEntity saveSubjectResult(NewSubjectResult newSubjectResult, MockExamResultEntity savedResult) {
        SubjectEntity subjectEntity = subjectJpaRepository.getReferenceById(newSubjectResult.subjectId());
        SubjectResultEntity subjectResultEntity = SubjectResultEntity.builder()
                .mockExamResultEntity(savedResult)
                .numberOfCorrect(newSubjectResult.numberOfCorrect())
                .totalTakenTime(newSubjectResult.totalTakenTime())
                .correctRate((int) ((double) newSubjectResult.numberOfCorrect() / subjectEntity.getNumberOfQuestions() * 100))
                .subjectEntity(subjectEntity)
                .score(newSubjectResult.score())
                .build();
        subjectResultJpaRepository.save(subjectResultEntity);
        return subjectResultEntity;
    }

    private void saveUserAnswer(NewUserAnswer newUserAnswer, SubjectResultEntity subjectResultEntity, UserEntity userEntity) {
        UserAnswerEntity userAnswerEntity = UserAnswerEntity.builder()
                .subjectResultEntity(subjectResultEntity)
                .questionEntity(questionJpaRepository.getReferenceById(newUserAnswer.questionId()))
                .userEntity(userEntity)
                .isCorrect(newUserAnswer.isCorrect())
                .selectOptionSeq(newUserAnswer.selectOptionSeq())
                .takenTime(newUserAnswer.takenTime())
                .build();
        userAnswerJpaRepository.save(userAnswerEntity);
    }
}
