package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.repository.MockExamJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamService {

    private final MockExamJpaRepository mockExamJpaRepository;

    public MockExamEntity getMockExam(Long mockExamId) {
        return mockExamJpaRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    public MockExamEntity getMockExam(CertificateEntity certificateEntity, Integer year, Integer round) {
        return mockExamJpaRepository.findMockExamByCertificateIdAndExamYearAndRound(certificateEntity.getId(), year, round)
                .orElseThrow(() -> new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    public List<MockExamEntity> getMockExamList(CertificateEntity certificateEntity, Integer year) {
        return mockExamJpaRepository.findMockExamByCertificateEntityAndExamYear(certificateEntity, year);
    }

    public List<Integer> getMockExamYears(CertificateEntity certificateEntity) {
        return mockExamJpaRepository.findMockExamYearsByCertificate(certificateEntity.getId());
    }

    public List<Integer> getMockExamRounds(CertificateEntity certificateEntity, Integer examYear) {
        return mockExamJpaRepository.findMockExamRoundsByCertificateEntityAndExamYear(certificateEntity.getId(), examYear);
    }

}
