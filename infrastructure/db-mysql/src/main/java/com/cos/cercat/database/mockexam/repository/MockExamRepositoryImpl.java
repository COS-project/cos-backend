package com.cos.cercat.database.mockexam.repository;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.database.mockexam.entity.MockExamEntity;
import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.domain.mockexam.*;
import com.cos.cercat.domain.mockexam.exception.MockExamNotFoundException;

import com.cos.cercat.domain.mockexam.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MockExamRepositoryImpl implements MockExamRepository {

    private final QuestionJpaRepository questionJpaRepository;
    private final MockExamJpaRepository mockExamJpaRepository;

    @Override
    public TargetMockExam save(NewMockExam newMockExam) {
        MockExamEntity entity = mockExamJpaRepository.save(MockExamEntity.from(newMockExam));
        return TargetMockExam.from(entity.getId());
    }

    @Override
    public void saveQuestions(TargetMockExam targetMockExam, List<NewQuestion> newQuestions) {

        List<QuestionEntity> questionEntities = newQuestions.stream()
                .map(newQuestion -> QuestionEntity.from(targetMockExam, newQuestion))
                .toList();

        questionJpaRepository.saveAll(questionEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Question readQuestion(Certificate certificate,
            MockExamSession mockExamSession,
            int questionSequence) {

        MockExamEntity mockExamEntity = mockExamJpaRepository.findMockExamByCertificateIdAndExamYearAndRound(
                certificate.id(),
                mockExamSession.examYear(),
                mockExamSession.round()
        ).orElseThrow(() -> MockExamNotFoundException.EXCEPTION);

        return questionJpaRepository.findQuestionByMockExamEntityAndQuestionSeq(mockExamEntity,
                questionSequence).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockExam> find(Certificate certificate, Integer examYear) {
        return mockExamJpaRepository.findMockExamByCertificateIdAndExamYear(certificate.id(),
                        examYear)
                .stream()
                .map(MockExamEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findQuestions(MockExam mockExam) {
        return questionJpaRepository.findByMockExamId(mockExam.id()).stream()
                .map(QuestionEntity::toDomain)
                .toList();
    }

    @Override
    public List<Integer> findExamYears(Certificate certificate) {
        return mockExamJpaRepository.findExamYearsByCertificateId(certificate.id());
    }

    @Override
    public MockExam find(TargetMockExam targetMockExam) {
        return mockExamJpaRepository.findById(targetMockExam.mockExamId())
                .orElseThrow(() -> MockExamNotFoundException.EXCEPTION)
                .toDomain();
    }

    @Override
    public Question findById(Long questionId) {
        return questionJpaRepository.findById(questionId)
                .orElseThrow(() -> QuestionNotFoundException.EXCEPTION)
                .toDomain();
    }

    public void updateQuestion(Question question) {
        QuestionEntity questionEntity = questionJpaRepository.findById(question.getQuestionId())
                .orElseThrow(() -> QuestionNotFoundException.EXCEPTION);
        questionEntity.update(question);
    }
}
