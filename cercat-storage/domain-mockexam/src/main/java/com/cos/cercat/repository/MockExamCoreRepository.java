package com.cos.cercat.repository;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.MockExamRepository;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MockExamCoreRepository implements MockExamRepository {

    private final QuestionJpaRepository questionJpaRepository;
    private final MockExamJpaRepository mockExamJpaRepository;


    @Override
    @Transactional
    public Question findQuestion(TargetCertificate targetCertificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {

        MockExamEntity mockExamEntity = mockExamJpaRepository.findMockExamByCertificateIdAndExamYearAndRound(
                targetCertificate.certificateId(),
                mockExamSession.examYear(),
                mockExamSession.round()
        ).orElseThrow(() -> new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));

        return questionJpaRepository.findQuestionByMockExamEntityAndQuestionSeq(mockExamEntity, questionSequence).toDomain();
    }
}
