package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.repository.MockExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamService {

    private final MockExamRepository mockExamRepository;

    public MockExam getMockExamById(Long mockExamId) {
        return mockExamRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    public List<MockExam> getMockExamByCertificateIdAndYear(Long certificateId, Integer year) {
        return mockExamRepository.findMockExamByCertificate_IdAndExamYear(certificateId, year);

    }

}
