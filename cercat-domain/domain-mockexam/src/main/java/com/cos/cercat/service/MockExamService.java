package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.repository.MockExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamService {

    private final MockExamRepository mockExamRepository;

    public MockExam getMockExam(Long mockExamId) {
        return mockExamRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    public MockExam getMockExam(CertificateEntity certificateEntity, Integer year, Integer round) {
        return mockExamRepository.findMockExamByCertificateEntityAndExamYearAndRound(certificateEntity, year, round);
    }

    public List<MockExam> getMockExamList(CertificateEntity certificateEntity, Integer year) {
        return mockExamRepository.findMockExamByCertificateEntityAndExamYear(certificateEntity, year);
    }

    public List<Integer> getMockExamYears(CertificateEntity certificateEntity) {
        return mockExamRepository.findMockExamYearsByCertificate(certificateEntity.getId());
    }

    public List<Integer> getMockExamRounds(CertificateEntity certificateEntity, Integer examYear) {
        return mockExamRepository.findMockExamRoundsByCertificateEntityAndExamYear(certificateEntity.getId(), examYear);
    }

}
